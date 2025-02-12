package com.example.parentapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.parentapp.firebase.FirebaseAuthManager
import com.example.parentapp.screens.ParentLoginScreen
import kotlinx.coroutines.launch

class ParentLoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController() // ✅ Added NavController

            ParentLoginScreen(
                navController = navController, // ✅ Pass NavController
                onLogin = { email, password -> login(email, password) },
                onRegister = { navController.navigate("register_child") } // ✅ Navigate correctly
            )
        }
    }

    private fun login(email: String, password: String) {
        lifecycleScope.launch {
            val user = FirebaseAuthManager.loginParent(email, password)
            if (user != null) {
                startActivity(Intent(this@ParentLoginActivity, ParentDashboardActivity::class.java))
                finish()
            } else {
                showToast()
            }
        }
    }

    private fun showToast() {
        Toast.makeText(this, "Login Failed! Check your email and password.", Toast.LENGTH_SHORT).show()
    }
}
