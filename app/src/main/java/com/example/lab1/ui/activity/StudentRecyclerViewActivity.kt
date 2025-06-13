package com.example.lab1.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.adapter.StudentRecyclerAdapter
import com.example.lab1.model.Student

class StudentRecyclerViewActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var studentAdapter: StudentRecyclerAdapter
    private val students = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_recyclerview)
        
        setupRecyclerView()
        loadStudentData()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.rv_students)
        studentAdapter = StudentRecyclerAdapter(students) { student ->
            Toast.makeText(this, "Clicked: ${student.name}", Toast.LENGTH_SHORT).show()
        }
        
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@StudentRecyclerViewActivity)
            adapter = studentAdapter
        }
    }

    private fun loadStudentData() {
        val sampleStudents = listOf(
            Student(1, "Nguyễn Văn A", "SE123456", "SE"),
            Student(2, "Trần Thị B", "SE123457", "SE"),
            Student(3, "Lê Văn C", "SE123458", "SE"),
            Student(4, "Phạm Thị D", "SE123459", "SE"),
            Student(5, "Hoàng Văn E", "SE123460", "SE"),
            Student(6, "Vũ Thị F", "SE123461", "SE"),
            Student(7, "Đặng Văn G", "SE123462", "SE"),
            Student(8, "Bùi Thị H", "SE123463", "SE"),
            Student(9, "Ngô Văn I", "SE123464", "SE"),
            Student(10, "Dương Thị K", "SE123465", "SE")
        )
        
        students.clear()
        students.addAll(sampleStudents)
        studentAdapter.notifyDataSetChanged()
    }
}