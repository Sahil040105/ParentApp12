package com.example.parentapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

@Composable
fun RegisterChildScreen(navController: NavController) {
    var childName by remember { mutableStateOf("") }
    var uniqueCode by remember { mutableStateOf("") }
    val database = FirebaseDatabase.getInstance().reference.child("child_users")

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Register Child", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = childName,
            onValueChange = { childName = it },
            label = { Text("Child Name") },
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = {
                val generatedCode = UUID.randomUUID().toString().substring(0, 8)
                uniqueCode = generatedCode

                val childData = mapOf(
                    "name" to childName,
                    "unique_code" to uniqueCode
                )

                database.child(uniqueCode).setValue(childData)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Generate Unique Code")
        }

        if (uniqueCode.isNotEmpty()) {
            Text("Child Unique Code: $uniqueCode", style = MaterialTheme.typography.bodyLarge)

            Button(
                onClick = { navController.navigate("home") },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Back to Home")
            }
        }
    }
}
