package com.example.parentapp.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.database.*

@Composable
fun ParentDashboardScreen(navController: NavController) {
    var sosMessage by remember { mutableStateOf("") }
    var childLocation by remember { mutableStateOf("Unknown") }

    LaunchedEffect(Unit) {
        listenForSOS { message -> sosMessage = message }
        listenForChildLocation { location -> childLocation = location }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Parent Dashboard", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))
        Text("SOS Alert: $sosMessage", color = MaterialTheme.colorScheme.error)

        Spacer(modifier = Modifier.height(20.dp))
        Text("Child's Last Location: $childLocation")
    }
}

private fun listenForSOS(onSOSReceived: (String) -> Unit) {
    val database = FirebaseDatabase.getInstance().reference.child("sos_alerts")
    database.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            snapshot.children.forEach {
                val alertMessage = it.child("alert").getValue(String::class.java) ?: ""
                onSOSReceived(alertMessage)
            }
        }
        override fun onCancelled(error: DatabaseError) {
            Log.e("ParentDashboard", "Error: ${error.message}")
        }
    })
}

private fun listenForChildLocation(onLocationReceived: (String) -> Unit) {
    val database = FirebaseDatabase.getInstance().reference.child("child_locations")
    database.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            snapshot.children.forEach {
                val lat = it.child("latitude").getValue(Double::class.java)
                val lon = it.child("longitude").getValue(Double::class.java)
                if (lat != null && lon != null) {
                    onLocationReceived("Lat: $lat, Lon: $lon")
                }
            }
        }
        override fun onCancelled(error: DatabaseError) {
            Log.e("ParentDashboard", "Error: ${error.message}")
        }
    })
}
