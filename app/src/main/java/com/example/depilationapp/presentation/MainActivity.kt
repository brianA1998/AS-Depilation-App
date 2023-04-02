package com.example.depilationapp.presentation

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.depilationapp.data.model.Client
import com.example.depilationapp.presentation.clients.components.ClientsScreen
import com.example.depilationapp.presentation.clients.components.DetailScreen
import com.example.depilationapp.presentation.navigation.Screen
import com.example.depilationapp.presentation.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

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
                    composable(Screen.DetailScreen.route, arguments = listOf(navArgument("client") {
                        type = NavType.StringType
                    })) { backStackEntry ->
                        val jsonClient = Uri.decode(backStackEntry.arguments?.getString("client"))
                        val client = Json.decodeFromString<Client>(jsonClient)
                        DetailScreen(client = client)
                    }
                }

            }
        }
    }
}


