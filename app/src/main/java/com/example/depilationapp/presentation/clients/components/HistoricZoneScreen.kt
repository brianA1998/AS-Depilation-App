package com.example.depilationapp.presentation.clients.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.depilationapp.presentation.zones.ZonesViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.depilationapp.data.model.ZoneDepilate


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun HistoricZoneScreen(navController: NavHostController, viewModel: ZonesViewModel, clientId: String) {
    LaunchedEffect(clientId) {
        viewModel.getGroupedZones(clientId)
    }
    val groupedZones = viewModel.groupedZones.value

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Historial de Zonas") },               navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = " Atrás")
                }
            })
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                groupedZones.forEach { (year, months) ->
                    ExpandableSection(title = "Año: $year") {
                        months.forEach { (month, zones) ->
                            ExpandableSection(title = "Mes: $month") {
                                zones.forEach { zone ->
                                    ZoneItem(zone = zone)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ZoneItem(zone: ZoneDepilate) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(text = "Zona: ${zone.zone}", fontWeight = FontWeight.Bold)
            Text(text = "Intensidad: ${zone.intense}", style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
fun ExpandableSection(title: String, content: @Composable () -> Unit) {
    val expandedState = remember { mutableStateOf(false) }
    val expanded = expandedState.value

    Column {
        Row(
            modifier = Modifier.clickable { expandedState.value = !expanded },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Icon(
                imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = null
            )
        }
        if (expanded) {
            content()
        }
    }
}

