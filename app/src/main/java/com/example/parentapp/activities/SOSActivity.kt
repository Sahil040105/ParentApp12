package com.example.parentapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.parentapp.firebase.FirebaseDatabaseManager
import kotlinx.coroutines.launch

class SOSActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // TODO: Implement Jetpack Compose UI with an SOS Button
        }
    }

    fun sendSOS(childId: String) {
        lifecycleScope.launch {  // ✅ Calling the suspend function in a coroutine
            FirebaseDatabaseManager.sendSOSAlert(childId)
        }
    }
}
