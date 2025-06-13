package com.example.lab1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.example.lab1.ui.activity.MainMenuActivity
import com.example.lab1.ui.screen.LoginScreen
import androidx.activity.compose.setContent
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(onLoginSuccess = { 
                Log.d("MainActivity", "Login success, starting MainMenuActivity")
                try {
                    val intent = Intent(this@MainActivity, MainMenuActivity::class.java)
                    // Clear task stack để tránh quay lại màn hình login
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error starting MainMenuActivity", e)
                    e.printStackTrace()
                }
            })
        }
    }
}
