package com.example.depilationapp.presentation.clients.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun ClientsScreen(navController: NavHostController) {
    Clients { clients ->
        ClientsContent(clients, navController)
    }
}

