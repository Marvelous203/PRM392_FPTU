package com.example.lab1.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.lab1.R

class OptionMenuActivity : AppCompatActivity() {
    private lateinit var tvOptionResult: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option_menu)
        
        // Thiết lập Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        
        // Ẩn title mặc định để dùng custom title
        supportActionBar?.setDisplayShowTitleEnabled(false)
        
        tvOptionResult = findViewById(R.id.tv_option_result)
        val btnBack = findViewById<Button>(R.id.btn_back_option)
        val btnMenu = findViewById<ImageButton>(R.id.btn_menu)
        
        // Xử lý click menu button
        btnMenu.setOnClickListener {
            showPopupMenu(it)
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
    
    private fun showPopupMenu(view: android.view.View) {
        val popup = androidx.appcompat.widget.PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.menu_option, popup.menu)
        
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_settings -> {
                    tvOptionResult.text = "Bạn đã chọn: Settings"
                    Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_about -> {
                    tvOptionResult.text = "Bạn đã chọn: About"
                    Toast.makeText(this, "About selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_exit -> {
                    tvOptionResult.text = "Bạn đã chọn: Exit"
                    Toast.makeText(this, "Exit selected", Toast.LENGTH_SHORT).show()
                    finish()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
    
    // Không cần override onCreateOptionsMenu và onOptionsItemSelected nữa
}