package com.example.lab1.model

data class Trainee(
    val id: Long = 0,
    val name: String,
    val email: String,
    val phone: String,
    val gender: String
) {
    // Constructor không có id (dùng cho tạo mới)
    constructor(name: String, email: String, phone: String, gender: String) : this(
        id = 0,
        name = name,
        email = email,
        phone = phone,
        gender = gender
    )
}