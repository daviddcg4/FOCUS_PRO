package com.example.focuspro.repository

import com.example.focuspro.model.user.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import android.util.Log

class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun addUserToFirestore(user: User) {
        try {
            db.collection("users").document(user.uid).set(user).await()
            Log.d("FirestoreRepository", "Usuario agregado exitosamente: ${user.uid}")
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Error al agregar usuario: ${e.message}")
        }
    }

    suspend fun getUserData(uid: String): User? {
        return try {
            val document = db.collection("users").document(uid).get().await()
            if (document.exists()) {
                document.toObject(User::class.java)
            } else {
                Log.e("FirestoreRepository", "Usuario no encontrado: $uid")
                null
            }
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Error al obtener datos del usuario: ${e.message}")
            null
        }
    }

    suspend fun updateUserInFirestore(user: User) {
        try {
            db.collection("users").document(user.uid).set(user).await()
            Log.d("FirestoreRepository", "Usuario actualizado exitosamente: ${user.uid}")
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Error al actualizar usuario: ${e.message}")
        }
    }

    suspend fun deleteUserFromFirestore(uid: String) {
        try {
            db.collection("users").document(uid).delete().await()
            Log.d("FirestoreRepository", "Usuario eliminado exitosamente: $uid")
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Error al eliminar usuario: ${e.message}")
        }
    }
}
