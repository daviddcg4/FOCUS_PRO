import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LanguageViewModel(private val context: Context) : ViewModel() {

    private val _selectedLanguage = MutableStateFlow(getCurrentLanguage())
    val selectedLanguage: StateFlow<String> = _selectedLanguage

    fun changeLanguage(languageCode: String) {
        // Guarda el idioma en SharedPreferences o cualquier otro almacenamiento
        saveLanguage(languageCode)
        _selectedLanguage.value = languageCode
        updateLanguage(context, languageCode)
    }

    private fun getCurrentLanguage(): String {
        val prefs = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        return prefs.getString("language", "en") ?: "en"
    }

    private fun saveLanguage(languageCode: String) {
        val prefs = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        prefs.edit().putString("language", languageCode).apply()
    }

    private fun updateLanguage(context: Context, languageCode: String) {
        val locale = java.util.Locale(languageCode)
        java.util.Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)

        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}
