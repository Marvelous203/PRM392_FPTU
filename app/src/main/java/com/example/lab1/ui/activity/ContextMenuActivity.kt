package com.example.lab1.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.lab1.R

class ContextMenuActivity : AppCompatActivity() {
    private lateinit var tvContextResult: TextView
    private lateinit var tvContextTarget: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_context_menu)
        
        tvContextResult = findViewById(R.id.tv_context_result)
        tvContextTarget = findViewById(R.id.tv_context_target)
        val btnBack = findViewById<Button>(R.id.btn_back_context)
        val btnChooseColor = findViewById<Button>(R.id.btn_choose_color)
        
        // Xử lý sự kiện click cho button chọn màu
        btnChooseColor.setOnClickListener {
            showColorPopupMenu(it as Button)
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
    
    private fun showColorPopupMenu(button: Button) {
        val popupMenu = PopupMenu(this, button)
        popupMenu.menuInflater.inflate(R.menu.menu_context, popupMenu.menu)
        
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.context_red -> {
                    tvContextTarget.setBackgroundColor(Color.RED)
                    tvContextTarget.setTextColor(Color.WHITE)
                    tvContextResult.text = "Bạn đã chọn màu: Đỏ"
                    Toast.makeText(this, "Đã chọn màu đỏ", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.context_yellow -> {
                    tvContextTarget.setBackgroundColor(Color.YELLOW)
                    tvContextTarget.setTextColor(Color.BLACK)
                    tvContextResult.text = "Bạn đã chọn màu: Vàng"
                    Toast.makeText(this, "Đã chọn màu vàng", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.context_blue -> {
                    tvContextTarget.setBackgroundColor(Color.BLUE)
                    tvContextTarget.setTextColor(Color.WHITE)
                    tvContextResult.text = "Bạn đã chọn màu: Xanh dương"
                    Toast.makeText(this, "Đã chọn màu xanh dương", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.context_green -> {
                    tvContextTarget.setBackgroundColor(Color.GREEN)
                    tvContextTarget.setTextColor(Color.WHITE)
                    tvContextResult.text = "Bạn đã chọn màu: Xanh lá"
                    Toast.makeText(this, "Đã chọn màu xanh lá", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.context_purple -> {
                    tvContextTarget.setBackgroundColor(Color.parseColor("#9C27B0"))
                    tvContextTarget.setTextColor(Color.WHITE)
                    tvContextResult.text = "Bạn đã chọn màu: Tím"
                    Toast.makeText(this, "Đã chọn màu tím", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.context_orange -> {
                    tvContextTarget.setBackgroundColor(Color.parseColor("#FF9800"))
                    tvContextTarget.setTextColor(Color.WHITE)
                    tvContextResult.text = "Bạn đã chọn màu: Cam"
                    Toast.makeText(this, "Đã chọn màu cam", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        
        popupMenu.show()
    }
}