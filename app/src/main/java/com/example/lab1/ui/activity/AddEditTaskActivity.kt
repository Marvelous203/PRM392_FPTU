package com.example.lab1.ui.activity

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R
import com.example.lab1.database.DatabaseHelper
import com.example.lab1.model.Task

class AddEditTaskActivity : AppCompatActivity() {
    
    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var spinnerPriority: Spinner
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private lateinit var tvTitle: TextView
    
    private lateinit var databaseHelper: DatabaseHelper
    private var taskId: Long = -1
    private var isEditMode = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_task)
        
        initViews()
        setupSpinner()
        checkEditMode()
        setupClickListeners()
        
        databaseHelper = DatabaseHelper(this)
    }
    
    private fun initViews() {
        tvTitle = findViewById(R.id.tv_add_edit_title)
        etTitle = findViewById(R.id.et_task_title)
        etDescription = findViewById(R.id.et_task_description)
        spinnerPriority = findViewById(R.id.spinner_priority)
        btnSave = findViewById(R.id.btn_save_task)
        btnCancel = findViewById(R.id.btn_cancel_task)
    }
    
    private fun setupSpinner() {
        val priorities = arrayOf("Low", "Medium", "High")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPriority.adapter = adapter
    }
    
    private fun checkEditMode() {
        taskId = intent.getLongExtra("task_id", -1)
        
        if (taskId != -1L) {
            // Edit mode
            isEditMode = true
            tvTitle.text = "Chỉnh Sửa Công Việc"
            btnSave.text = "CẬP NHẬT"
            
            // Load task data
            val title = intent.getStringExtra("task_title") ?: ""
            val description = intent.getStringExtra("task_description") ?: ""
            val priority = intent.getStringExtra("task_priority") ?: "Medium"
            
            etTitle.setText(title)
            etDescription.setText(description)
            
            // Set spinner selection
            val priorities = arrayOf("Low", "Medium", "High")
            val priorityIndex = priorities.indexOf(priority)
            if (priorityIndex != -1) {
                spinnerPriority.setSelection(priorityIndex)
            }
        } else {
            // Add mode
            isEditMode = false
            tvTitle.text = "Thêm Công Việc Mới"
            btnSave.text = "THÊM"
        }
    }
    
    private fun setupClickListeners() {
        btnSave.setOnClickListener {
            saveTask()
        }
        
        btnCancel.setOnClickListener {
            finish()
        }
    }
    
    private fun saveTask() {
        val title = etTitle.text.toString().trim()
        val description = etDescription.text.toString().trim()
        val priority = spinnerPriority.selectedItem.toString()
        
        // Validation
        if (title.isEmpty()) {
            etTitle.error = "Vui lòng nhập tiêu đề"
            etTitle.requestFocus()
            return
        }
        
        if (description.isEmpty()) {
            etDescription.error = "Vui lòng nhập mô tả"
            etDescription.requestFocus()
            return
        }
        
        if (isEditMode) {
            // Update existing task
            val existingTask = databaseHelper.getTaskById(taskId)
            if (existingTask != null) {
                val updatedTask = existingTask.copy(
                    title = title,
                    description = description,
                    priority = priority
                )
                
                val result = databaseHelper.updateTask(updatedTask)
                if (result > 0) {
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Lỗi khi cập nhật công việc", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            // Add new task
            val newTask = Task(
                title = title,
                description = description,
                priority = priority,
                createdAt = databaseHelper.getCurrentDateTime()
            )
            
            val result = databaseHelper.addTask(newTask)
            if (result != -1L) {
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Lỗi khi thêm công việc", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        databaseHelper.close()
    }
}