package com.example.parentapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.parentapp.firebase.FirebaseDatabaseManager
import com.example.parentapp.screens.ChildLoginScreen
import kotlinx.coroutines.launch

class ChildLoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChildLoginScreen { uniqueCode -> loginWithCode(uniqueCode) }
        }
    }

    private fun loginWithCode(uniqueCode: String) {
        lifecycleScope.launch {
            val childId = FirebaseDatabaseManager.checkChildCode(uniqueCode)
            if (childId != null) {
                val intent = Intent(this@ChildLoginActivity, ChildDashboardActivity::class.java)
                intent.putExtra("CHILD_ID", childId)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@ChildLoginActivity, "Invalid code", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
