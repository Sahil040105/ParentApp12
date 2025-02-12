package com.example.parentapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onParentLogin: () -> Unit, onChildLogin: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Select Login Type", style = MaterialTheme.typography.headlineMedium)

        Button(
            onClick = onParentLogin,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Login as Parent")
        }

        Button(
            onClick = onChildLogin,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Login as Child")
        }
    }
}
