package com.example.depilationapp.presentation.clients.components

import androidx.compose.runtime.Composable
import com.example.depilationapp.presentation.zones.ZonesViewModel
import androidx.compose.runtime.collectAsState


@Composable
fun HistoricZoneScreen(viewModel: ZonesViewModel, clientId: String) {
    val groupedZones by viewModel.getGroupedZones(clientId).collectAsState(initial = emptyMap())

    //...
}
