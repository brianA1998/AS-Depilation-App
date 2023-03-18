package com.example.depilationapp.presentation.navigation

import com.example.depilationapp.data.model.Client

sealed class Screen(val route: String) {
    object ClientsScreen : Screen("clients")
    object DetailScreen : Screen("detail/{clientName}") {
        fun createRoute(clientName: String) = "detail/$clientName"
    }
}

