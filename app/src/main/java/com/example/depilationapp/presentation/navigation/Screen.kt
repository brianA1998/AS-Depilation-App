package com.example.depilationapp.presentation.navigation

import android.net.Uri
import com.example.depilationapp.data.model.Client

sealed class Screen(val route: String) {
    object ClientsScreen : Screen("clients")
    object DetailScreen : Screen("detail/{client}") {
        fun createRoute(jsonClient: String) = "detail/${Uri.encode(jsonClient)}"
    }
    object AddClientScreen : Screen("add_client")
}

