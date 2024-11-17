package com.example.focuspro.settings.language

import android.content.Context
import android.content.SharedPreferences

class LanguageManager(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    fun getLanguage(): String {
        return preferences.getString("language", "en") ?: "en" // Idioma por defecto: inglés
    }

    fun saveLanguage(languageCode: String) {
        preferences.edit().putString("language", languageCode).apply()
    }
}
