package com.example.focuspro.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemeViewModel : ViewModel() {

    // Estado de si el tema oscuro está activo
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    // Método para alternar el tema
    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }
}
