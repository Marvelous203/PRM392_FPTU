package com.example.lab1.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.adapter.ProductAdapter
import com.example.lab1.model.Product

class ProductRecyclerViewActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private val products = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_recyclerview)
        
        setupRecyclerView()
        loadProductData()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.rv_products)
        productAdapter = ProductAdapter(products) { product ->
            Toast.makeText(this, "Clicked: ${product.name} - ${product.price} VND", Toast.LENGTH_SHORT).show()
        }
        
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ProductRecyclerViewActivity)
            adapter = productAdapter
        }
    }

    private fun loadProductData() {
        val sampleProducts = listOf(
            Product(1, "iPhone 15 Pro", 29990000.0, "Điện thoại thông minh cao cấp từ Apple", "drawable://" + R.drawable.product_iphone),
            Product(2, "Samsung Galaxy S24", 22990000.0, "Flagship Android với camera AI tiên tiến", "drawable://" + R.drawable.product_samsung),
            Product(3, "MacBook Air M3", 34990000.0, "Laptop siêu mỏng với chip M3 mạnh mẽ", "drawable://" + R.drawable.product_macbook),
            Product(4, "iPad Pro 12.9", 26990000.0, "Máy tính bảng chuyên nghiệp cho sáng tạo", "drawable://" + R.drawable.product_ipad),
            Product(5, "AirPods Pro 2", 6490000.0, "Tai nghe không dây với chống ồn chủ động", "drawable://" + R.drawable.product_airpods),
            Product(6, "Apple Watch Series 9", 9990000.0, "Đồng hồ thông minh với tính năng sức khỏe", "drawable://" + R.drawable.product_watch),
            Product(7, "Dell XPS 13", 25990000.0, "Ultrabook Windows cao cấp", "drawable://" + R.drawable.product_dell),
            Product(8, "Sony WH-1000XM5", 8990000.0, "Tai nghe chống ồn hàng đầu", "drawable://" + R.drawable.product_sony),
            Product(9, "Nintendo Switch OLED", 8490000.0, "Máy chơi game cầm tay và console", "drawable://" + R.drawable.product_nintendo),
            Product(10, "Logitech MX Master 3S", 2490000.0, "Chuột không dây cho chuyên gia", "drawable://" + R.drawable.product_logitech)
        )
        
        products.clear()
        products.addAll(sampleProducts)
        productAdapter.notifyDataSetChanged()
    }
}