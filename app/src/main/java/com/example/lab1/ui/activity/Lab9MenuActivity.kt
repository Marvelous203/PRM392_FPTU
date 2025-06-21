package com.example.lab1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R

class Lab9MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab9_menu)
        
        val btnTaskManager = findViewById<Button>(R.id.btn_task_manager)
        val btnBack = findViewById<Button>(R.id.btn_back_lab9)
        
        btnTaskManager.setOnClickListener {
            startActivity(Intent(this, TaskManagerActivity::class.java))
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
}