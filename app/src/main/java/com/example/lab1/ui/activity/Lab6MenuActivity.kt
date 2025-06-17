package com.example.lab1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R

class Lab6MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab6_menu)
        
        val btnOptionMenu = findViewById<Button>(R.id.btn_option_menu)
        val btnPopupMenu = findViewById<Button>(R.id.btn_popup_menu)
        val btnContextMenu = findViewById<Button>(R.id.btn_context_menu)
        val btnBack = findViewById<Button>(R.id.btn_back)
        
        btnOptionMenu.setOnClickListener {
            startActivity(Intent(this, OptionMenuActivity::class.java))
        }
        
        btnPopupMenu.setOnClickListener {
            startActivity(Intent(this, PopupMenuActivity::class.java))
        }
        
        btnContextMenu.setOnClickListener {
            startActivity(Intent(this, ContextMenuActivity::class.java))
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
}