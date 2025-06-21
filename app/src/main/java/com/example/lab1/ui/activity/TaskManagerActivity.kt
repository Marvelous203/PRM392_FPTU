package com.example.lab1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.adapter.TaskAdapter
import com.example.lab1.database.DatabaseHelper
import com.example.lab1.model.Task

class TaskManagerActivity : AppCompatActivity() {
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var btnAddTask: Button
    private lateinit var btnBack: Button
    private lateinit var btnShowCompleted: Button
    private lateinit var btnShowPending: Button
    private lateinit var btnShowAll: Button
    
    private lateinit var databaseHelper: DatabaseHelper
    private var tasks = mutableListOf<Task>()
    
    companion object {
        const val REQUEST_ADD_TASK = 1001
        const val REQUEST_EDIT_TASK = 1002
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_manager)
        
        initViews()
        setupRecyclerView()
        setupClickListeners()
        
        databaseHelper = DatabaseHelper(this)
        loadTasks()
    }
    
    private fun initViews() {
        recyclerView = findViewById(R.id.rv_tasks)
        btnAddTask = findViewById(R.id.btn_add_task)
        btnBack = findViewById(R.id.btn_back_task_manager)
        btnShowCompleted = findViewById(R.id.btn_show_completed)
        btnShowPending = findViewById(R.id.btn_show_pending)
        btnShowAll = findViewById(R.id.btn_show_all)
    }
    
    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            tasks = tasks,
            onTaskClick = { task ->
                // Hiển thị chi tiết task
                showTaskDetails(task)
            },
            onTaskStatusChange = { updatedTask ->
                // Cập nhật trạng thái task
                updateTaskStatus(updatedTask)
            },
            onTaskEdit = { task ->
                // Chỉnh sửa task
                editTask(task)
            },
            onTaskDelete = { task ->
                // Xóa task
                deleteTask(task)
            }
        )
        
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter
    }
    
    private fun setupClickListeners() {
        btnAddTask.setOnClickListener {
            val intent = Intent(this, AddEditTaskActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD_TASK)
        }
        
        btnBack.setOnClickListener {
            finish()
        }
        
        btnShowAll.setOnClickListener {
            loadTasks()
        }
        
        btnShowCompleted.setOnClickListener {
            loadTasksByStatus(true)
        }
        
        btnShowPending.setOnClickListener {
            loadTasksByStatus(false)
        }
    }
    
    private fun loadTasks() {
        val allTasks = databaseHelper.getAllTasks()
        tasks.clear()
        tasks.addAll(allTasks)
        taskAdapter.notifyDataSetChanged()
    }
    
    private fun loadTasksByStatus(isCompleted: Boolean) {
        val filteredTasks = databaseHelper.getTasksByStatus(isCompleted)
        tasks.clear()
        tasks.addAll(filteredTasks)
        taskAdapter.notifyDataSetChanged()
    }
    
    private fun showTaskDetails(task: Task) {
        AlertDialog.Builder(this)
            .setTitle(task.title)
            .setMessage(
                "Mô tả: ${task.description}\n\n" +
                "Độ ưu tiên: ${task.priority}\n" +
                "Trạng thái: ${if (task.isCompleted) "Đã hoàn thành" else "Chưa hoàn thành"}\n" +
                "Ngày tạo: ${task.createdAt}"
            )
            .setPositiveButton("OK", null)
            .show()
    }
    
    private fun updateTaskStatus(updatedTask: Task) {
        val result = databaseHelper.updateTask(updatedTask)
        if (result > 0) {
            taskAdapter.updateTask(updatedTask)
            Toast.makeText(this, "Cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Lỗi khi cập nhật trạng thái", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun editTask(task: Task) {
        val intent = Intent(this, AddEditTaskActivity::class.java)
        intent.putExtra("task_id", task.id)
        intent.putExtra("task_title", task.title)
        intent.putExtra("task_description", task.description)
        intent.putExtra("task_priority", task.priority)
        startActivityForResult(intent, REQUEST_EDIT_TASK)
    }
    
    private fun deleteTask(task: Task) {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc chắn muốn xóa công việc \"${task.title}\"?")
            .setPositiveButton("Xóa") { _, _ ->
                val result = databaseHelper.deleteTask(task.id)
                if (result > 0) {
                    taskAdapter.removeTask(task)
                    Toast.makeText(this, "Xóa công việc thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Lỗi khi xóa công việc", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_ADD_TASK -> {
                    loadTasks() // Reload danh sách sau khi thêm
                    Toast.makeText(this, "Thêm công việc thành công", Toast.LENGTH_SHORT).show()
                }
                REQUEST_EDIT_TASK -> {
                    loadTasks() // Reload danh sách sau khi sửa
                    Toast.makeText(this, "Cập nhật công việc thành công", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        databaseHelper.close()
    }
}