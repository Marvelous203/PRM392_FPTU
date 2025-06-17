package com.example.lab1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R

class Lab8MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab8_menu)
        
        val btnSendNotification: Button = findViewById(R.id.btn_send_notification)
        val btnBack: Button = findViewById(R.id.btn_back_lab8)
        
        btnSendNotification.setOnClickListener {
            startActivity(Intent(this, SendNotificationActivity::class.java))
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
}