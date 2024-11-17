package com.example.focuspro

import LanguageViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.focuspro.navigation.NavGraph
import com.example.focuspro.repository.AuthRepository
import com.example.focuspro.repository.FirestoreRepository

import com.example.focuspro.ui.theme.FocusProTheme
import com.example.focuspro.viewmodel.AuthViewModel
import com.example.focuspro.viewmodel.AuthViewModelFactory

class MainActivity : ComponentActivity() {

    // Instancia de FirestoreRepository
    private val firestoreRepository: FirestoreRepository by lazy { FirestoreRepository() }

    // Instancia de AuthViewModel usando un ViewModelFactory personalizado
    private val authViewModel: AuthViewModel by viewModels { AuthViewModelFactory(AuthRepository(firestoreRepository)) }

    // Instancia de LanguageViewModel manualmente con el contexto de la aplicación
    private val languageViewModel: LanguageViewModel by lazy { LanguageViewModel(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aplicamos el tema de la app
            FocusProTheme {
                // Superficie principal de la app
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainApp(authViewModel = authViewModel, languageViewModel = languageViewModel)
                }
            }
        }
    }
}

// Función Composable principal que define la estructura de la app
@Composable
fun MainApp(authViewModel: AuthViewModel, languageViewModel: LanguageViewModel) {
    // Recordamos el controlador de navegación para manejar las pantallas
    val navController = rememberNavController()

    // Verificamos si el usuario ya está autenticado para definir la pantalla inicial
    authViewModel.checkCurrentUser()

    // Configuramos la navegación a través de NavGraph
    NavGraph(
        navController = navController,
        authViewModel = authViewModel,
        languageViewModel = languageViewModel
    )
}
