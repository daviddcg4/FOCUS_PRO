package com.example.focuspro

import LanguageViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.example.focuspro.navigation.NavGraph
import com.example.focuspro.repository.AuthRepository
import com.example.focuspro.repository.FirestoreRepository
import com.example.focuspro.ui.theme.FocusProTheme
import com.example.focuspro.viewmodel.AuthViewModel
import com.example.focuspro.viewmodel.AuthViewModelFactory
import com.example.focuspro.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {

    // Instancia de FirestoreRepository
    private val firestoreRepository: FirestoreRepository by lazy { FirestoreRepository() }

    // Instancia de AuthViewModel usando un ViewModelFactory personalizado
    private val authViewModel: AuthViewModel by viewModels { AuthViewModelFactory(AuthRepository(firestoreRepository)) }

    // Instancia de LanguageViewModel manualmente con el contexto de la aplicación
    private val languageViewModel: LanguageViewModel by lazy { LanguageViewModel(applicationContext) }

    // Instancia de ThemeViewModel
    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Observamos el estado del tema (oscuro o claro) desde el ThemeViewModel
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            // Aplicamos el tema de la app según el estado actual
            FocusProTheme(darkTheme = isDarkTheme) {
                // Superficie principal de la app
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainApp(
                        authViewModel = authViewModel,
                        languageViewModel = languageViewModel,
                        themeViewModel = themeViewModel // Pasamos ThemeViewModel a la app
                    )
                }
            }
        }
    }
}

// Función Composable principal que define la estructura de la app
@Composable
fun MainApp(
    authViewModel: AuthViewModel,
    languageViewModel: LanguageViewModel,
    themeViewModel: ThemeViewModel
) {

    val navController = rememberNavController()

    // Verificamos si el usuario ya está autenticado para definir la pantalla inicial
    authViewModel.checkCurrentUser()

    // Configuramos la navegación a través de NavGraph
    NavGraph(
        navController = navController,
        authViewModel = authViewModel,
        languageViewModel = languageViewModel,
        themeViewModel = themeViewModel
    )
}
