package com.example.lab1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R

class Lab1MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab1_menu)
        
        val btnSubjectList = findViewById<Button>(R.id.btn_subject_list)
        val btnLoginRegister = findViewById<Button>(R.id.btn_login_register)
        val btnBack = findViewById<Button>(R.id.btn_back_main)

        btnSubjectList.setOnClickListener {
            startActivity(Intent(this, SubjectListActivity::class.java))
        }
        
        btnLoginRegister.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
}