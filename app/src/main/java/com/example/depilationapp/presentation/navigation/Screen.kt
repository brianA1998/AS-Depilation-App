package com.example.depilationapp.presentation.navigation

import android.net.Uri
import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.model.ZoneDepilate
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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

    object IntensityZoneScreen : Screen("intensity_zone/{clientId}/{zones}") {
        fun createRoute(clientId: String, zones: List<ZoneDepilate>): String {
            val zonesString = Json.encodeToString(zones)
            return "intensity_zone/${Uri.encode(clientId)}/${Uri.encode(zonesString)}"
        }
    }

}



