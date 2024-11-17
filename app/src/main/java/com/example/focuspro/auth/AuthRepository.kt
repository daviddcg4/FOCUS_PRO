package com.example.focuspro.repository

import com.example.focuspro.model.user.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepository(private val firestoreRepository: FirestoreRepository) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun registerUser(email: String, password: String, name: String): FirebaseUser? {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = User(uid = result.user?.uid ?: "", name = name, email = email)
            firestoreRepository.addUserToFirestore(user) // Agrega el usuario a Firestore
            result.user
        } catch (e: Exception) {
            // Manejar errores
            null
        }
    }

    suspend fun loginUser(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            // Manejar errores
            null
        }
    }

    fun logoutUser() {
        auth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    // Método para cambiar la contraseña del usuario
    suspend fun changeUserPassword(user: FirebaseUser, newPassword: String): Boolean {
        return try {
            user.updatePassword(newPassword).await() // Asegúrate de usar Kotlin Coroutines para hacer esta llamada
            true
        } catch (e: Exception) {
            false
        }
    }
}
