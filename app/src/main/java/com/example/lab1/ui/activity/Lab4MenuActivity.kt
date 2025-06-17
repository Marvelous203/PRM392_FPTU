package com.example.lab1.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R

class Lab4MenuActivity : AppCompatActivity() {
    private lateinit var tvSelectedFood: TextView
    private lateinit var tvSelectedDrink: TextView
    private lateinit var btnChooseFood: Button
    private lateinit var btnChooseDrink: Button
    private lateinit var tvSummaryView: TextView
    private lateinit var btnBack: Button
    
    // Biến để lưu thông tin đã chọn
    private var selectedFood: String? = null
    private var selectedDrink: String? = null
    private var foodPrice: Int = 0
    private var drinkPrice: Int = 0
    
    private val foodLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedFood = result.data?.getStringExtra("selected_food")
            foodPrice = result.data?.getIntExtra("food_price", 0) ?: 0
            tvSelectedFood.text = "Món ăn đã chọn: $selectedFood"
            updateSummary()
        }
    }
    
    private val drinkLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedDrink = result.data?.getStringExtra("selected_drink")
            drinkPrice = result.data?.getIntExtra("drink_price", 0) ?: 0
            tvSelectedDrink.text = "Đồ uống đã chọn: $selectedDrink"
            updateSummary()
        }
    }
    private val summaryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val summary = result.data?.getStringExtra("summary")
            tvSummaryView.text = summary ?: "Không có thông tin"
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab4_menu)
        
        initViews()
        setupButtons()
    }
    
    private fun initViews() {
        tvSelectedFood = findViewById(R.id.tv_selected_food)
        tvSelectedDrink = findViewById(R.id.tv_selected_drink)
        btnChooseFood = findViewById(R.id.btn_choose_food)
        btnChooseDrink = findViewById(R.id.btn_choose_drink)
        tvSummaryView = findViewById(R.id.tv_summary_view)
        btnBack = findViewById(R.id.btn_back)
    }
    
    private fun setupButtons() {
        btnChooseFood.setOnClickListener {
            val intent = Intent(this, FoodActivity::class.java)
            foodLauncher.launch(intent)
        }
        
        btnChooseDrink.setOnClickListener {
            val intent = Intent(this, DrinkActivity::class.java)
            drinkLauncher.launch(intent)
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
    
    private fun updateSummary() {
        val summaryText = StringBuilder()
        summaryText.append("=== TÓM TẮT ĐƠN HÀNG ===\n\n")
        
        var totalPrice = 0
        
        if (selectedFood != null) {
            summaryText.append(" $selectedFood: ${formatPrice(foodPrice)}\n")
            totalPrice += foodPrice
        }
        
        if (selectedDrink != null) {
            summaryText.append(" $selectedDrink: ${formatPrice(drinkPrice)}\n")
            totalPrice += drinkPrice
        }
        
        if (totalPrice > 0) {
            summaryText.append("\n" + "=".repeat(25) + "\n")
            summaryText.append(" TỔNG TIỀN: ${formatPrice(totalPrice)}")
        } else {
            summaryText.append("Chưa có món nào được chọn")
        }
        
        tvSummaryView.text = summaryText.toString()
    }
    
    private fun formatPrice(price: Int): String {
        return String.format("%,d VNĐ", price)
    }
}