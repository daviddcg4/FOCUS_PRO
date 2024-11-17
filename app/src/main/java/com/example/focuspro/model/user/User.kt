package com.example.focuspro.model.user

data class User(
    val uid: String = "", // Firebase User ID
    val name: String = "", // User's name
    val email: String = "", // User's email
    val cycleCount: Int = 0 // Number of completed Pomodoro cycles
)
