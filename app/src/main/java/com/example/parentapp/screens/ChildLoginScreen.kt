package com.example.parentapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ChildLoginScreen(onLogin: (String) -> Unit) {
    var uniqueCode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(text = "Child Login", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = uniqueCode,
            onValueChange = { uniqueCode = it },
            label = { Text("Unique Code") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onLogin(uniqueCode) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}
