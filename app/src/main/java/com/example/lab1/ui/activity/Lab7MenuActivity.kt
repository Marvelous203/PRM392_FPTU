package com.example.lab1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R

class Lab7MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab7_menu)
        
        val btnCameraPermission = findViewById<Button>(R.id.btn_camera_permission)
        val btnBack = findViewById<Button>(R.id.btn_back_lab7)
        
        btnCameraPermission.setOnClickListener {
            startActivity(Intent(this, CameraPermissionActivity::class.java))
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
}