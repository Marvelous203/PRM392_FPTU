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

class FoodActivity : AppCompatActivity() {
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnOrder: Button
    private lateinit var btnCancel: Button
    
    // Map để lưu giá của từng món
    private val foodPrices = mapOf(
        "Phở Hà Nội" to 50000,
        "Bún Bò Huế" to 45000,
        "Mì Quảng" to 40000,
        "Hủ Tíu Sài Gòn" to 35000
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        
        initViews()
        setupButtons()
    }
    
    private fun initViews() {
        radioGroup = findViewById(R.id.radio_group_food)
        btnOrder = findViewById(R.id.btn_order)
        btnCancel = findViewById(R.id.btn_cancel)
    }
    
    private fun setupButtons() {
        btnOrder.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(selectedId)
                val fullText = selectedRadioButton.text.toString()
                val foodName = fullText.split(" - ")[0] // Lấy tên món
                val price = foodPrices[foodName] ?: 0
                
                val resultIntent = Intent()
                resultIntent.putExtra("selected_food", foodName)
                resultIntent.putExtra("food_price", price)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Vui lòng chọn một món ăn", Toast.LENGTH_SHORT).show()
            }
        }
        
        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}