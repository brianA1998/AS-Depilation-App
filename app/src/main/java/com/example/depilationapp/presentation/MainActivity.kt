package com.example.depilationapp.presentation

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.depilationapp.data.model.Client
import com.example.depilationapp.presentation.clients.components.ClientsScreen
import com.example.depilationapp.presentation.clients.components.DetailScreen
import com.example.depilationapp.presentation.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint
import darkThemeColors
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import com.example.depilationapp.domain.use_case.UseCases
import com.example.depilationapp.presentation.clients.components.AddClientScreen
import lightThemeColors
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var useCases: UseCases


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(useCases)
        }
    }
}

@Composable
fun MyApp(useCases: UseCases) {
    val isSystemInDarkTheme = isSystemInDarkTheme()
    val colors = if (isSystemInDarkTheme) darkThemeColors() else lightThemeColors()
    MaterialTheme(
        colors = colors,
    ) {
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
            composable(Screen.AddClientScreen.route) {
                AddClientScreen(navController = navController, useCases = useCases)
            }

        }

    }
}


