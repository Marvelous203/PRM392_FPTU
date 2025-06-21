package com.example.lab1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.adapter.TraineeAdapter
import com.example.lab1.model.Trainee
import com.example.lab1.repository.TraineeRepository

class TraineeManagerActivity : AppCompatActivity() {
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var traineeAdapter: TraineeAdapter
    private lateinit var btnAddTrainee: Button
    private lateinit var btnRefresh: Button
    private lateinit var btnBack: Button
    private lateinit var tvTraineeCount: TextView
    private lateinit var progressBar: ProgressBar
    
    private val traineeRepository = TraineeRepository()
    
    companion object {
        const val REQUEST_ADD_TRAINEE = 1001
        const val REQUEST_EDIT_TRAINEE = 1002
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainee_manager)
        
        initViews()
        setupRecyclerView()
        setupClickListeners()
        loadTrainees()
    }
    
    private fun initViews() {
        recyclerView = findViewById(R.id.rv_trainees)
        btnAddTrainee = findViewById(R.id.btn_add_trainee)
        btnRefresh = findViewById(R.id.btn_refresh_trainees)
        btnBack = findViewById(R.id.btn_back_trainee_manager)
        tvTraineeCount = findViewById(R.id.tv_trainee_count)
        progressBar = findViewById(R.id.progress_bar)
    }
    
    private fun setupRecyclerView() {
        traineeAdapter = TraineeAdapter(
            onEditClick = { trainee -> editTrainee(trainee) },
            onDeleteClick = { trainee -> confirmDeleteTrainee(trainee) }
        )
        
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@TraineeManagerActivity)
            adapter = traineeAdapter
        }
    }
    
    private fun setupClickListeners() {
        btnAddTrainee.setOnClickListener {
            val intent = Intent(this, AddEditTraineeActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD_TRAINEE)
        }
        
        btnRefresh.setOnClickListener {
            loadTrainees()
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
    
    private fun loadTrainees() {
        showLoading(true)
        traineeRepository.getAllTrainees(object : TraineeRepository.TraineeCallback<List<Trainee>> {
            override fun onSuccess(data: List<Trainee>) {
                showLoading(false)
                traineeAdapter.updateTrainees(data)
                updateTraineeCount(data.size)
            }
            
            override fun onError(message: String) {
                showLoading(false)
                Toast.makeText(this@TraineeManagerActivity, message, Toast.LENGTH_LONG).show()
            }
        })
    }
    
    private fun editTrainee(trainee: Trainee) {
        val intent = Intent(this, AddEditTraineeActivity::class.java).apply {
            putExtra("trainee_id", trainee.id)
            putExtra("trainee_name", trainee.name)
            putExtra("trainee_email", trainee.email)
            putExtra("trainee_phone", trainee.phone)
            putExtra("trainee_gender", trainee.gender)
            putExtra("is_edit_mode", true)
        }
        startActivityForResult(intent, REQUEST_EDIT_TRAINEE)
    }
    
    private fun confirmDeleteTrainee(trainee: Trainee) {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc chắn muốn xóa nhân viên ${trainee.name}?")
            .setPositiveButton("Xóa") { _, _ ->
                deleteTrainee(trainee)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
    
    private fun deleteTrainee(trainee: Trainee) {
        showLoading(true)
        traineeRepository.deleteTrainee(trainee.id, object : TraineeRepository.TraineeCallback<Unit> {
            override fun onSuccess(data: Unit) {
                showLoading(false)
                traineeAdapter.removeTrainee(trainee)
                updateTraineeCount(traineeAdapter.itemCount)
                Toast.makeText(this@TraineeManagerActivity, "Đã xóa nhân viên thành công", Toast.LENGTH_SHORT).show()
            }
            
            override fun onError(message: String) {
                showLoading(false)
                Toast.makeText(this@TraineeManagerActivity, message, Toast.LENGTH_LONG).show()
            }
        })
    }
    
    private fun updateTraineeCount(count: Int) {
        tvTraineeCount.text = "Tổng số nhân viên: $count"
    }
    
    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) ProgressBar.VISIBLE else ProgressBar.GONE
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_ADD_TRAINEE, REQUEST_EDIT_TRAINEE -> {
                    // Reload danh sách sau khi thêm/sửa
                    loadTrainees()
                }
            }
        }
    }
}