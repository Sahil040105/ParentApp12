package com.example.parentapp.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

object FirebaseAuthManager {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    // Sign up Parent
    suspend fun signUpParent(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Login Parent
    suspend fun loginParent(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Logout
    fun logout() {
        auth.signOut()
    }

    // 🔥 Login Child using Unique Code
    suspend fun loginChild(uniqueCode: String, callback: (Boolean) -> Unit) {
        try {
            val snapshot = database.child("children").child(uniqueCode).get().await()
            val exists = snapshot.exists()
            callback(exists) // If the child exists in the database, login successful
        } catch (e: Exception) {
            e.printStackTrace()
            callback(false)
        }
    }
}
