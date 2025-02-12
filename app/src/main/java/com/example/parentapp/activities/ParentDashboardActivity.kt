package com.example.parentapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class ParentDashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // TODO: Add Jetpack Compose UI
        }
    }

    fun trackChildLocation() {
        // Call FirebaseLocationService to get child's live location
    }

    fun checkScreenTime() {
        // Fetch screen time details from Firebase
    }
}
