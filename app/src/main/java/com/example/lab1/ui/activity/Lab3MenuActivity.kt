package com.example.lab1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R

class Lab3MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab3_menu)
        
        val btnBasicListView = findViewById<Button>(R.id.btn_basic_listview)
        val btnCustomListView = findViewById<Button>(R.id.btn_custom_listview)
        val btnBack = findViewById<Button>(R.id.btn_back_main)
        
        btnBasicListView.setOnClickListener {
            startActivity(Intent(this, BasicListViewActivity::class.java))
        }
        
        btnCustomListView.setOnClickListener {
            startActivity(Intent(this, CustomListViewActivity::class.java))
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
}