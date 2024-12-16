package com.example.focuspro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focuspro.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    // Estado del usuario actual
    private val _currentUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser: StateFlow<FirebaseUser?> get() = _currentUser

    // Estado para indicar éxito o error en el registro
    private val _registrationSuccess = MutableStateFlow<Boolean?>(null)
    val registrationSuccess: StateFlow<Boolean?> get() = _registrationSuccess

    // Estado para indicar éxito o error en el inicio de sesión
    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> get() = _loginSuccess

    // Estado para indicar éxito o error en el cambio de contraseña
    private val _passwordChangeSuccess = MutableStateFlow<Boolean?>(null)
    val passwordChangeSuccess: StateFlow<Boolean?> get() = _passwordChangeSuccess

    init {
        checkCurrentUser()
    }

    fun checkCurrentUser() {
        _currentUser.value = authRepository.getCurrentUser()
    }

    fun registerUser(email: String, password: String, name: String) {
        viewModelScope.launch {
            val user = authRepository.registerUser(email, password, name)
            // logica para el registro
            if (user != null) {
                _currentUser.value = user
                _registrationSuccess.value = true // Registro exitoso
            } else {
                _registrationSuccess.value = false // Error en registro
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val user = authRepository.loginUser(email, password)
            // logica post inicio de sesion
            if (user != null) {
                _currentUser.value = user
                _loginSuccess.value = true // Inicio de sesión exitoso
            } else {
                _loginSuccess.value = false // Error en inicio de sesión
            }
        }
    }

    fun logoutUser() {
        authRepository.logoutUser()
        _currentUser.value = null
    }

    // Nuevo método para cambiar la contraseña
    fun changePassword(newPassword: String) {
        val user = _currentUser.value
        if (user != null) {
            viewModelScope.launch {
                val success = authRepository.changeUserPassword(user, newPassword)
                if (success) {
                    _passwordChangeSuccess.value = true // Cambio de contraseña exitoso
                } else {
                    _passwordChangeSuccess.value = false // Error en cambio de contraseña
                }
            }
        }
    }

    // FIXME: Resetear el estado de éxito después de la navegación
    fun resetRegistrationState() {
        _registrationSuccess.value = null
    }

    fun resetLoginState() {
        _loginSuccess.value = null
    }

    // FIXME: Resetear el estado de cambio de contraseña
    fun resetPasswordChangeState() {
        _passwordChangeSuccess.value = null
    }

}
