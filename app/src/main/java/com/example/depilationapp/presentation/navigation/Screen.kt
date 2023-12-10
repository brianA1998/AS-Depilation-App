package com.example.depilationapp.presentation.navigation

import android.net.Uri
import com.example.depilationapp.data.model.Client

sealed class Screen(val route: String) {
    object ClientsScreen : Screen("clients")
    object DetailScreen : Screen("detail/{client}") {
        fun createRoute(jsonClient: String) = "detail/${Uri.encode(jsonClient)}"
    }
    object AddClientScreen : Screen("add_client")

    object HistoricZoneScreen : Screen("historic/{clientId}") {
        fun createRoute(clientId: String) = "historic/${clientId}"
    }

    object EditClientScreen : Screen("edit/{client}") {
        fun createRoute(jsonClient: String) = "edit/${Uri.encode(jsonClient)}"
    }

    object EditIntensityZoneScreen : Screen("edit_intensity/{client}/{zones}") {
        fun createRoute(jsonClient: String, zones: String) =
            "edit_intensity/${Uri.encode(jsonClient)}/${Uri.encode(zones)}"
    }

}

