package com.example.lab1.ui.activity

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R

class BasicListViewActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var etNewItem: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnBack: Button
    
    private val items = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_listview)
        
        initViews()
        setupListView()
        setupButtons()
        
        // Thêm dữ liệu mẫu
        items.addAll(listOf("Android", "iOS", "Flutter", "React Native", "Xamarin"))
        adapter.notifyDataSetChanged()
    }
    
    private fun initViews() {
        listView = findViewById(R.id.listView)
        etNewItem = findViewById(R.id.et_new_item)
        btnAdd = findViewById(R.id.btn_add)
        btnBack = findViewById(R.id.btn_back)
    }
    
    private fun setupListView() {
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter
        
        // Long click để hiển thị menu Sửa/Xóa
        listView.setOnItemLongClickListener { _, _, position, _ ->
            showEditDeleteDialog(position)
            true
        }
    }
    
    private fun setupButtons() {
        btnAdd.setOnClickListener {
            addItem()
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
    
    private fun addItem() {
        val newItem = etNewItem.text.toString().trim()
        if (newItem.isNotEmpty()) {
            items.add(newItem)
            adapter.notifyDataSetChanged()
            etNewItem.text.clear()
            Toast.makeText(this, "Đã thêm: $newItem", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Vui lòng nhập nội dung", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showEditDeleteDialog(position: Int) {
        val options = arrayOf("Sửa", "Xóa")
        
        AlertDialog.Builder(this)
            .setTitle("Chọn hành động")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> editItem(position)
                    1 -> deleteItem(position)
                }
            }
            .show()
    }
    
    private fun editItem(position: Int) {
        val currentItem = items[position]
        val editText = EditText(this)
        editText.setText(currentItem)
        
        AlertDialog.Builder(this)
            .setTitle("Sửa item")
            .setView(editText)
            .setPositiveButton("Lưu") { _, _ ->
                val newText = editText.text.toString().trim()
                if (newText.isNotEmpty()) {
                    items[position] = newText
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "Đã cập nhật", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
    
    private fun deleteItem(position: Int) {
        val itemToDelete = items[position]
        
        AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc muốn xóa \"$itemToDelete\"?")
            .setPositiveButton("Xóa") { _, _ ->
                items.removeAt(position)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Đã xóa: $itemToDelete", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
}