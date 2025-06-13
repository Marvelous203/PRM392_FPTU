package com.example.lab1.model

data class Student(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val avatarResId: Int = 0 // Optional avatar resource ID
)