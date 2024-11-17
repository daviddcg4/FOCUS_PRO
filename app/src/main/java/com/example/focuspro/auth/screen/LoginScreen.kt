import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.focuspro.viewmodel.AuthViewModel
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.focuspro.R
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(authViewModel: AuthViewModel, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) } // Estado para aceptar términos
    var showTermsDialog by remember { mutableStateOf(false) } // Estado para mostrar el diálogo

    val currentUser by authViewModel.currentUser.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    // Password visibility
    var isPasswordVisible by remember { mutableStateOf(false) }

    // Obtener los términos y condiciones desde los recursos locales
    val termsText = stringResource(id = R.string.terms_and_conditions)

    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    // Mostrar el diálogo de términos si aún no se han aceptado
    if (showTermsDialog) {
        AlertDialog(
            onDismissRequest = { showTermsDialog = false },
            title = { Text(text = stringResource(id = R.string.terms_title)) },
            text = { Text(text = termsText) },
            confirmButton = {
                TextButton(
                    onClick = {
                        termsAccepted = true
                        showTermsDialog = false // Cierra el diálogo
                    }
                ) {
                    Text(text = stringResource(id = R.string.accept_terms_button))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showTermsDialog = false } // Cierra el diálogo si se cancela
                ) {
                    Text(text = stringResource(id = R.string.decline_terms_button))
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(id = R.string.login_email_label)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.login_password_label)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (isPasswordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            trailingIcon = {
                val icon = if (isPasswordVisible) {
                    Icons.Filled.Done
                } else {
                    Icons.Filled.Clear
                }
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = if (isPasswordVisible) stringResource(id = R.string.hide_password_description) else stringResource(id = R.string.show_password_description),
                        tint = Color.Gray
                    )
                }
            },
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Hacer que el botón de login esté habilitado solo si se aceptaron los términos
        Button(
            onClick = {
                if (termsAccepted) {
                    coroutineScope.launch {
                        authViewModel.loginUser(email, password)
                    }
                } else {
                    showTermsDialog = true // Mostrar el diálogo si los términos no se aceptaron
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = termsAccepted // Deshabilitar el botón si no se han aceptado los términos
        ) {
            Text(stringResource(id = R.string.login_button))
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { navController.navigate("register") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.register_button))
        }

        // Muestra un mensaje de error si hay alguno
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Mostrar los términos y condiciones
        Spacer(modifier = Modifier.height(16.dp))
        if (!termsAccepted) {
            TextButton(
                onClick = { showTermsDialog = true } // Mostrar el diálogo al presionar
            ) {
                Text(stringResource(id = R.string.acept_terms_button))
            }
        }
    }
}
