package com.example.lab1.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnSignIn: Button
    private lateinit var btnRegister: Button
    private lateinit var btnBack: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        initViews()
        setupClickListeners()
    }
    
    private fun initViews() {
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        btnSignIn = findViewById(R.id.btn_sign_in)
        btnRegister = findViewById(R.id.btn_register)
        btnBack = findViewById(R.id.btn_back)
    }
    
    private fun setupClickListeners() {
        btnSignIn.setOnClickListener {
            handleLogin()
        }
        
        btnRegister.setOnClickListener {
            handleRegister()
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
    
    private fun handleLogin() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()
        
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Kiểm tra đăng nhập đơn giản
        if (username == "admin" && password == "1234") {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
            // Có thể chuyển đến màn hình khác hoặc trở về menu
            finish()
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun handleRegister() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()
        
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Xử lý đăng ký đơn giản
        Toast.makeText(this, "Registration successful for: $username", Toast.LENGTH_SHORT).show()
        
        // Clear fields after registration
        etUsername.setText("")
        etPassword.setText("")
    }
}