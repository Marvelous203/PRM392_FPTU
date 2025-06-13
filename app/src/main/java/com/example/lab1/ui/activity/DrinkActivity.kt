package com.example.lab1.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R

class DrinkActivity : AppCompatActivity() {
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnOrder: Button
    private lateinit var btnCancel: Button
    
    // Map để lưu giá của từng đồ uống
    private val drinkPrices = mapOf(
        "Pepsi" to 15000,
        "Heineken" to 25000,
        "Tiger" to 22000,
        "Sài Gòn Đỏ" to 20000
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink)
        
        initViews()
        setupButtons()
    }
    
    private fun initViews() {
        radioGroup = findViewById(R.id.radio_group_drink)
        btnOrder = findViewById(R.id.btn_order)
        btnCancel = findViewById(R.id.btn_cancel)
    }
    
    private fun setupButtons() {
        btnOrder.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(selectedId)
                val fullText = selectedRadioButton.text.toString()
                val drinkName = fullText.split(" - ")[0] // Lấy tên đồ uống
                val price = drinkPrices[drinkName] ?: 0
                
                val resultIntent = Intent()
                resultIntent.putExtra("selected_drink", drinkName)
                resultIntent.putExtra("drink_price", price)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Vui lòng chọn một đồ uống", Toast.LENGTH_SHORT).show()
            }
        }
        
        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}