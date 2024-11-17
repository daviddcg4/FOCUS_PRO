package com.example.focuspro.settings.language

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LanguageViewModel(context: Context) : ViewModel() {
    private val languageManager = LanguageManager(context)

    // Estado para el idioma seleccionado
    private val _selectedLanguage = MutableStateFlow(languageManager.getLanguage())
    val selectedLanguage: StateFlow<String> get() = _selectedLanguage

    // Cambia el idioma y actualiza el estado
    fun changeLanguage(languageCode: String) {
        viewModelScope.launch {
            languageManager.saveLanguage(languageCode)
            _selectedLanguage.value = languageCode
        }
    }
}
