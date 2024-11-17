import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.focuspro.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import androidx.compose.ui.res.stringResource
import com.example.focuspro.R

@Composable
fun LoginScreen(authViewModel: AuthViewModel, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val currentUser by authViewModel.currentUser.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    // Password visibility
    var isPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(currentUser) {
        // Si hay un usuario autenticado, navega a la pantalla principal (home)
        if (currentUser != null) {
            navController.navigate("home") {
                popUpTo("login") {
                    inclusive = true
                } // Cierra la pantalla de login para que no vuelva
            }
        }
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
        Button(
            onClick = {
                coroutineScope.launch {
                    authViewModel.loginUser(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth()
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
    }
}
