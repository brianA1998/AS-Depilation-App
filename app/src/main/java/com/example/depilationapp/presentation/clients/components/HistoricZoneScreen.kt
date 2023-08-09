package com.example.depilationapp.presentation.clients.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import com.example.depilationapp.presentation.zones.ZonesViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HistoricZoneScreen(viewModel: ZonesViewModel, clientId: String) {
    val groupedZones = viewModel.groupedZones.value

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Historial de Zonas") })
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                groupedZones.forEach { (year, months) ->
                    ExpandableSection(title = "Año: $year") {
                        months.forEach { (month, zones) ->
                            ExpandableSection(title = "Mes: $month") {
                                zones.forEach { zone ->
                                    Text(
                                        text = "Zona: ${zone.zone}, Intensidad: ${zone.intense}",
                                        style = MaterialTheme.typography.body1
                                    )
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
