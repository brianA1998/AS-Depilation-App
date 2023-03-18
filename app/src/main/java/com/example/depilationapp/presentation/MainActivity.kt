package com.example.depilationapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.depilationapp.presentation.clients.components.ClientsScreen
import com.example.depilationapp.presentation.clients.components.DetailScreen
import com.example.depilationapp.presentation.navigation.Screen
import com.example.depilationapp.presentation.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = Screen.ClientsScreen.route) {
                    composable(Screen.ClientsScreen.route) {
                        ClientsScreen(navController = navController)
                    }
                    composable(Screen.DetailScreen.route) { backStackEntry ->
                        val clientName = backStackEntry.arguments?.getString("clientName") ?: ""
                        DetailScreen(clientName = clientName)
                    }
                }

            }
        }
    }
}


