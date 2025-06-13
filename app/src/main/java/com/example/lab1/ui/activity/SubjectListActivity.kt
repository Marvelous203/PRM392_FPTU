package com.example.lab1.ui.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R

class SubjectListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_list)
        
        val btnBack = findViewById<Button>(R.id.btn_back_to_lab1_menu)
        
        btnBack.setOnClickListener {
            finish()
        }
    }
}