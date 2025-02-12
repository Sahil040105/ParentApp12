package com.example.parentapp.screens

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.parentapp.services.LocationService
import com.google.firebase.database.FirebaseDatabase

@Composable
fun ChildDashboardScreen(navController: NavController, childId: String) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        startLocationService(context)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Child Dashboard", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { sendSOS(childId, context) }) {
            Text("SOS Emergency 🚨")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { navController.popBackStack() }) { // ✅ ADDED: Navigate back to previous screen
            Text("Logout")
        }
    }
}

private fun startLocationService(context: Context) {
    val intent = Intent(context, LocationService::class.java)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        context.startForegroundService(intent)
    } else {
        context.startService(intent)
    }
}

private fun sendSOS(childId: String, context: Context) {
    val database = FirebaseDatabase.getInstance().reference.child("sos_alerts").child(childId)
    database.setValue(mapOf("alert" to "SOS Triggered!", "timestamp" to System.currentTimeMillis()))
}
