package com.example.lab1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R

class Lab2MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab2_menu)
        
        val btnRandomNumber = findViewById<Button>(R.id.btn_random_number)
        val btnCalculator = findViewById<Button>(R.id.btn_calculator)
        val btnLoginSystem = findViewById<Button>(R.id.btn_login_system)
        val btnBack = findViewById<Button>(R.id.btn_back_main)
        
        btnRandomNumber.setOnClickListener {
            // TODO: Tạo RandomNumberActivity
             startActivity(Intent(this, RandomNumberActivity::class.java))
        }
        
        btnCalculator.setOnClickListener {
            // TODO: Tạo CalculatorActivity
             startActivity(Intent(this, CalculatorActivity::class.java))
        }
        
        btnLoginSystem.setOnClickListener {
            // TODO: Tạo LoginSystemActivity
            // startActivity(Intent(this, LoginSystemActivity::class.java))
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
}