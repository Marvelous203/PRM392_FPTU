package com.example.lab1.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R
import com.example.lab1.adapter.StudentAdapter
import com.example.lab1.model.Student

class CustomListViewActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var btnBack: Button
    private lateinit var adapter: StudentAdapter
    
    private val students = listOf(
        Student(1, "Nguyễn Văn An", "an.nguyen@email.com", "0123456789"),
        Student(2, "Trần Thị Bình", "binh.tran@email.com", "0987654321"),
        Student(3, "Lê Văn Cường", "cuong.le@email.com", "0111222333"),
        Student(4, "Phạm Thị Dung", "dung.pham@email.com", "0444555666"),
        Student(5, "Hoàng Văn Em", "em.hoang@email.com", "0777888999"),
        Student(6, "Vũ Thị Phương", "phuong.vu@email.com", "0333444555"),
        Student(7, "Đặng Văn Giang", "giang.dang@email.com", "0666777888"),
        Student(8, "Bùi Thị Hoa", "hoa.bui@email.com", "0999000111")
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_listview)
        
        initViews()
        setupListView()
        setupButtons()
    }
    
    private fun initViews() {
        listView = findViewById(R.id.listView)
        btnBack = findViewById(R.id.btn_back)
    }
    
    private fun setupListView() {
        adapter = StudentAdapter(this, students)
        listView.adapter = adapter
        
        listView.setOnItemClickListener { _, _, position, _ ->
            val student = students[position]
            Toast.makeText(this, "Clicked: ${student.name}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun setupButtons() {
        btnBack.setOnClickListener {
            finish()
        }
    }
}