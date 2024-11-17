package com.example.focuspro.taskManager.model.entity

import com.google.firebase.database.Exclude


// Modelo de datos para una tarea
data class Task(
    var id: String? = null,               // ID para Firebase que se genera de forma automatica
    val title: String = "",
    val isCompleted: Boolean = false,
    val priority: TaskPriority = TaskPriority.LOW,
    val createdById: String = ""
) {
    // Constructor vacío para Firebase
    constructor() : this(null, "", false, TaskPriority.LOW, createdById = "")

    // Excluir ID del envío a Firebase
    @Exclude
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "title" to title,
        "isCompleted" to isCompleted,
        "priority" to priority.name
    )
}
