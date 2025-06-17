package com.example.lab1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        
        val btnLab1 = findViewById<Button>(R.id.btn_lab1)
        val btnLab2 = findViewById<Button>(R.id.btn_lab2)
        val btnLab3 = findViewById<Button>(R.id.btn_lab3)
        val btnLab4 = findViewById<Button>(R.id.btn_lab4)
        val btnLab5 = findViewById<Button>(R.id.btn_lab5)
        val btnLab6 = findViewById<Button>(R.id.btn_lab6)
        val btnLab7 = findViewById<Button>(R.id.btn_lab7)
        
        btnLab1.setOnClickListener {
            startActivity(Intent(this, Lab1MenuActivity::class.java))
        }
        
        btnLab2.setOnClickListener {
            startActivity(Intent(this, Lab2MenuActivity::class.java))
        }
        
        btnLab3.setOnClickListener {
            startActivity(Intent(this, Lab3MenuActivity::class.java))
        }
        
        btnLab4.setOnClickListener {
            startActivity(Intent(this, Lab4MenuActivity::class.java))
        }
        
        btnLab5.setOnClickListener {
            startActivity(Intent(this, Lab5MenuActivity::class.java))
        }
        
        btnLab6.setOnClickListener {
            startActivity(Intent(this, Lab6MenuActivity::class.java))
        }
        
        btnLab7.setOnClickListener {
            startActivity(Intent(this, Lab7MenuActivity::class.java))
        }
    }
}