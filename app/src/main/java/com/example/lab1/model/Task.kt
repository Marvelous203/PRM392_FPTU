package com.example.lab1.model

data class Task(
    val id: Long = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val createdAt: String,
    val priority: String = "Medium" // Low, Medium, High
) {
    constructor() : this(0, "", "", false, "", "Medium")
}