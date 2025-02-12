package com.example.parentapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.parentapp.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParentApp()
        }
    }
}

@Composable
fun ParentApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeScreen(
                onParentLogin = { navController.navigate("parent_login") },
                onChildLogin = { navController.navigate("child_login") }
            )
        }

        composable("parent_login") {
            ParentLoginScreen(
                navController = navController,
                onLogin = { _, _ -> navController.navigate("parent_dashboard") }, // ✅ Fixed unused parameters
                onRegister = { navController.navigate("register_child") }
            )
        }

        composable("parent_dashboard") {
            ParentDashboardScreen(navController)
        }

        composable("register_child") {
            RegisterChildScreen(navController)
        }

        composable("child_login") {
            ChildLoginScreen { uniqueCode ->
                navController.navigate("child_dashboard/$uniqueCode")
            }
        }

        composable(
            "child_dashboard/{childId}",
            arguments = listOf(navArgument("childId") { type = NavType.StringType })
        ) { backStackEntry ->
            val childId = backStackEntry.arguments?.getString("childId") ?: ""
            ChildDashboardScreen(navController, childId)
        }
    }
}
