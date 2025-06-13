package com.example.lab1.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R

class CalculatorActivity : AppCompatActivity() {
    private lateinit var etFirstNumber: EditText
    private lateinit var etSecondNumber: EditText
    private lateinit var tvResult: TextView
    private lateinit var btnAdd: Button
    private lateinit var btnSubtract: Button
    private lateinit var btnMultiply: Button
    private lateinit var btnDivide: Button
    private lateinit var btnBack: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        
        etFirstNumber = findViewById(R.id.et_first_number)
        etSecondNumber = findViewById(R.id.et_second_number)
        tvResult = findViewById(R.id.tv_result)
        btnAdd = findViewById(R.id.btn_add)
        btnSubtract = findViewById(R.id.btn_subtract)
        btnMultiply = findViewById(R.id.btn_multiply)
        btnDivide = findViewById(R.id.btn_divide)
        btnBack = findViewById(R.id.btn_back)
        
        btnAdd.setOnClickListener {
            calculate("+")
        }
        
        btnSubtract.setOnClickListener {
            calculate("-")
        }
        
        btnMultiply.setOnClickListener {
            calculate("*")
        }
        
        btnDivide.setOnClickListener {
            calculate("/")
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
    
    private fun calculate(operator: String) {
        val firstNumber = etFirstNumber.text.toString().toDoubleOrNull()
        val secondNumber = etSecondNumber.text.toString().toDoubleOrNull()
        
        if (firstNumber == null || secondNumber == null) {
            tvResult.text = "Vui lòng nhập số hợp lệ"
            return
        }
        
        val result = when (operator) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "*" -> firstNumber * secondNumber
            "/" -> {
                if (secondNumber == 0.0) {
                    tvResult.text = "Không thể chia cho 0"
                    return
                }
                firstNumber / secondNumber
            }
            else -> 0.0
        }
        
        tvResult.text = result.toString()
    }
}