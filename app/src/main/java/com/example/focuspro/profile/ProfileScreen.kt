package com.example.focuspro.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.focuspro.viewmodel.AuthViewModel
import androidx.compose.ui.res.stringResource
import com.example.focuspro.R

@Composable
fun ProfileScreen(authViewModel: AuthViewModel, navController: NavController) {
    var displayName by remember { mutableStateOf(authViewModel.currentUser.value?.displayName ?: "") }
    var email by remember { mutableStateOf(authViewModel.currentUser.value?.email ?: "") }
    var isEditingPassword by remember { mutableStateOf(false) }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    var passwordChangeError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(stringResource(id = R.string.profile_title), fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        // Show user information
        Text("${stringResource(id = R.string.display_name_label)} $displayName", fontSize = 18.sp)
        Text("${stringResource(id = R.string.email_label)} $email", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))

        // Option to change password
        TextButton(onClick = { isEditingPassword = !isEditingPassword }) {
            Text(stringResource(id = R.string.change_password_button), fontSize = 18.sp)
        }

        if (isEditingPassword) {
            Spacer(modifier = Modifier.height(16.dp))
            PasswordChangeSection(
                newPassword = newPassword,
                confirmNewPassword = confirmNewPassword,
                onNewPasswordChange = { newPassword = it },
                onConfirmNewPasswordChange = { confirmNewPassword = it },
                onChangePassword = {
                    if (newPassword == confirmNewPassword) {
                        // Call the function from the ViewModel to change the password
                        authViewModel.changePassword(newPassword) // Ensure this function is implemented
                        passwordChangeError = null // Reset error message
                    } else {
                        //FIXME: Revisar esta linea
//                        passwordChangeError = stringResource(id = R.string.passwords_do_not_match)
                    }
                },
                errorMessage = passwordChangeError
            )
        }

        Spacer(modifier = Modifier.weight(1f)) // Push content upwards

        // Logout button
        LogoutButton(onLogout = {
            authViewModel.logoutUser()
            navController.navigate("login") // Navigate to the login screen
        })
    }
}

@Composable
fun PasswordChangeSection(
    newPassword: String,
    confirmNewPassword: String,
    onNewPasswordChange: (String) -> Unit,
    onConfirmNewPasswordChange: (String) -> Unit,
    onChangePassword: () -> Unit,
    errorMessage: String?
) {
    Column {
        TextField(
            value = newPassword,
            onValueChange = onNewPasswordChange,
            label = { Text(stringResource(id = R.string.new_password_label)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = confirmNewPassword,
            onValueChange = onConfirmNewPasswordChange,
            label = { Text(stringResource(id = R.string.confirm_new_password_label)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (newPassword == confirmNewPassword) {
                    Icons.Filled.Done
                } else {
                    Icons.Filled.Clear
                }
                Icon(
                    imageVector = icon,
                    contentDescription = "Password match",
                    tint = if (newPassword == confirmNewPassword) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    }
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onChangePassword,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.change_password))
        }

        // Show error message if exists
        if (!errorMessage.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun LogoutButton(onLogout: () -> Unit) {
    Button(
        onClick = onLogout,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
    ) {
        Icon(imageVector = Icons.Filled.Close, contentDescription = "Logout")
        Spacer(modifier = Modifier.width(8.dp)) // Spacing between icon and text
        Text(stringResource(id = R.string.logout))
    }
}
