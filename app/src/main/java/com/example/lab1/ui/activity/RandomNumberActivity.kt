package com.example.lab1.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R
import kotlin.random.Random

class RandomNumberActivity : AppCompatActivity() {
    private lateinit var tvRandomNumber: TextView
    private lateinit var btnGenerate: Button
    private lateinit var btnBack: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_number)
        
        tvRandomNumber = findViewById(R.id.tv_random_number)
        btnGenerate = findViewById(R.id.btn_generate)
        btnBack = findViewById(R.id.btn_back)
        
        btnGenerate.setOnClickListener {
            val randomNumber = Random.nextInt(1, 100)
            tvRandomNumber.text = randomNumber.toString()
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
}