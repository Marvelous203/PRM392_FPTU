package com.example.lab1.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R

class PopupMenuActivity : AppCompatActivity() {
    private lateinit var tvPopupResult: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup_menu)
        
        tvPopupResult = findViewById(R.id.tv_popup_result)
        val btnShowPopup = findViewById<Button>(R.id.btn_show_popup)
        val btnBack = findViewById<Button>(R.id.btn_back_popup)
        
        btnShowPopup.setOnClickListener {
            showPopupMenu(it as Button)
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
    
    private fun showPopupMenu(view: Button) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_popup, popupMenu.menu)
        
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.popup_edit -> {
                    tvPopupResult.text = "Bạn đã chọn: Edit"
                    Toast.makeText(this, "Edit selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.popup_delete -> {
                    tvPopupResult.text = "Bạn đã chọn: Delete"
                    Toast.makeText(this, "Delete selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.popup_share -> {
                    tvPopupResult.text = "Bạn đã chọn: Share"
                    Toast.makeText(this, "Share selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.popup_copy -> {
                    tvPopupResult.text = "Bạn đã chọn: Copy"
                    Toast.makeText(this, "Copy selected", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        
        popupMenu.show()
    }
}