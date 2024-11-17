import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.Locale

class LanguageManager(private val context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    // Obtiene el idioma actual
    fun getLanguage(): String {
        return preferences.getString("language", Locale.getDefault().language) ?: "en"
    }

    // Cambia el idioma y actualiza la configuración del sistema
    fun setLanguage(languageCode: String) {
        preferences.edit().putString("language", languageCode).apply()
        updateLanguage(languageCode)
    }

    // Actualiza la configuración global
    private fun updateLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}
