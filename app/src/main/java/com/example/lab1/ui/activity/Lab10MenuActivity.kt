package com.example.lab1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R

class Lab10MenuActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab10_menu)
        
        initViews()
        setupClickListeners()
    }
    
    private fun initViews() {
        // Views are initialized through findViewById in setupClickListeners
    }
    
    private fun setupClickListeners() {
        val btnTraineeManager = findViewById<Button>(R.id.btn_trainee_manager)
        val btnBackLab10 = findViewById<Button>(R.id.btn_back_lab10)
        
        btnTraineeManager.setOnClickListener {
            startActivity(Intent(this, TraineeManagerActivity::class.java))
        }
        
        btnBackLab10.setOnClickListener {
            finish()
        }
    }
}