package com.example.lab1.ui.activity

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R
import com.example.lab1.model.Trainee
import com.example.lab1.repository.TraineeRepository

class AddEditTraineeActivity : AppCompatActivity() {
    
    private lateinit var tvTitle: TextView
    private lateinit var etTraineeName: EditText
    private lateinit var etTraineeEmail: EditText
    private lateinit var etTraineePhone: EditText
    private lateinit var spinnerGender: Spinner
    private lateinit var btnSaveTrainee: Button
    private lateinit var btnCancelTrainee: Button
    private lateinit var progressBar: ProgressBar
    
    private val traineeRepository = TraineeRepository()
    private var isEditMode = false
    private var traineeId: Long = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_trainee)
        
        initViews()
        setupSpinner()
        loadDataFromIntent()
        setupClickListeners()
    }
    
    private fun initViews() {
        tvTitle = findViewById(R.id.tv_add_edit_trainee_title)
        etTraineeName = findViewById(R.id.et_trainee_name)
        etTraineeEmail = findViewById(R.id.et_trainee_email)
        etTraineePhone = findViewById(R.id.et_trainee_phone)
        spinnerGender = findViewById(R.id.spinner_trainee_gender)
        btnSaveTrainee = findViewById(R.id.btn_save_trainee)
        btnCancelTrainee = findViewById(R.id.btn_cancel_trainee)
        progressBar = findViewById(R.id.progress_bar_trainee)
    }
    
    private fun setupSpinner() {
        val genderOptions = arrayOf("Nam", "Nữ", "Khác")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapter
    }
    
    private fun loadDataFromIntent() {
        isEditMode = intent.getBooleanExtra("is_edit_mode", false)
        
        if (isEditMode) {
            tvTitle.text = "Sửa Thông Tin Nhân Viên"
            btnSaveTrainee.text = "CẬP NHẬT"
            
            traineeId = intent.getLongExtra("trainee_id", 0)
            etTraineeName.setText(intent.getStringExtra("trainee_name"))
            etTraineeEmail.setText(intent.getStringExtra("trainee_email"))
            etTraineePhone.setText(intent.getStringExtra("trainee_phone"))
            
            val gender = intent.getStringExtra("trainee_gender")
            val genderPosition = when (gender) {
                "Nam" -> 0
                "Nữ" -> 1
                "Khác" -> 2
                else -> 0
            }
            spinnerGender.setSelection(genderPosition)
        } else {
            tvTitle.text = "Thêm Nhân Viên Mới"
            btnSaveTrainee.text = "THÊM"
        }
    }
    
    private fun setupClickListeners() {
        btnSaveTrainee.setOnClickListener {
            if (validateInput()) {
                if (isEditMode) {
                    updateTrainee()
                } else {
                    createTrainee()
                }
            }
        }
        
        btnCancelTrainee.setOnClickListener {
            finish()
        }
    }
    
    private fun validateInput(): Boolean {
        val name = etTraineeName.text.toString().trim()
        val email = etTraineeEmail.text.toString().trim()
        val phone = etTraineePhone.text.toString().trim()
        
        when {
            name.isEmpty() -> {
                etTraineeName.error = "Vui lòng nhập tên nhân viên"
                etTraineeName.requestFocus()
                return false
            }
            email.isEmpty() -> {
                etTraineeEmail.error = "Vui lòng nhập email"
                etTraineeEmail.requestFocus()
                return false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                etTraineeEmail.error = "Email không hợp lệ"
                etTraineeEmail.requestFocus()
                return false
            }
            phone.isEmpty() -> {
                etTraineePhone.error = "Vui lòng nhập số điện thoại"
                etTraineePhone.requestFocus()
                return false
            }
            phone.length < 10 -> {
                etTraineePhone.error = "Số điện thoại phải có ít nhất 10 số"
                etTraineePhone.requestFocus()
                return false
            }
        }
        
        return true
    }
    
    private fun createTrainee() {
        val trainee = Trainee(
            name = etTraineeName.text.toString().trim(),
            email = etTraineeEmail.text.toString().trim(),
            phone = etTraineePhone.text.toString().trim(),
            gender = spinnerGender.selectedItem.toString()
        )
        
        showLoading(true)
        traineeRepository.createTrainee(trainee, object : TraineeRepository.TraineeCallback<Trainee> {
            override fun onSuccess(data: Trainee) {
                showLoading(false)
                Toast.makeText(this@AddEditTraineeActivity, "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            }
            
            override fun onError(message: String) {
                showLoading(false)
                Toast.makeText(this@AddEditTraineeActivity, message, Toast.LENGTH_LONG).show()
            }
        })
    }
    
    private fun updateTrainee() {
        val trainee = Trainee(
            id = traineeId,
            name = etTraineeName.text.toString().trim(),
            email = etTraineeEmail.text.toString().trim(),
            phone = etTraineePhone.text.toString().trim(),
            gender = spinnerGender.selectedItem.toString()
        )
        
        showLoading(true)
        traineeRepository.updateTrainee(traineeId, trainee, object : TraineeRepository.TraineeCallback<Trainee> {
            override fun onSuccess(data: Trainee) {
                showLoading(false)
                Toast.makeText(this@AddEditTraineeActivity, "Cập nhật nhân viên thành công", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            }
            
            override fun onError(message: String) {
                showLoading(false)
                Toast.makeText(this@AddEditTraineeActivity, message, Toast.LENGTH_LONG).show()
            }
        })
    }
    
    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) ProgressBar.VISIBLE else ProgressBar.GONE
        btnSaveTrainee.isEnabled = !show
    }
}