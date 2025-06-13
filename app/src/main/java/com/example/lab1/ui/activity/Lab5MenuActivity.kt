package com.example.lab1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R

class Lab5MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab5_menu)
        
        val btnStudentList = findViewById<Button>(R.id.btn_student_list)
        val btnProductList = findViewById<Button>(R.id.btn_product_list)
        val btnBack = findViewById<Button>(R.id.btn_back)
        
        btnStudentList.setOnClickListener {
            startActivity(Intent(this, StudentRecyclerViewActivity::class.java))
        }
        
        btnProductList.setOnClickListener {
            startActivity(Intent(this, ProductRecyclerViewActivity::class.java))
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
}