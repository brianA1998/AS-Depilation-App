package com.example.depilationapp.presentation.zones

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.depilationapp.data.model.ZoneDepilate

@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun IntensityZoneScreen(navController: NavHostController, clientId: String, zonesString: String, viewModel: ZonesViewModel) {
    LaunchedEffect(clientId) {
        viewModel.getGroupedZones(clientId)
    }
    val groupedZones = viewModel.groupedZones.value

    var showSaveButton by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Intensidades de Zonas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = " Atrás")
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Puedes mostrar las zonas en forma de lista, por ejemplo:
                groupedZones.forEach { (year, months) ->
                    // Puedes organizarlas como desees
                    Text(text = "Año: $year", fontWeight = FontWeight.Bold)
                    months.forEach { (month, zones) ->
                        Text(text = "Mes: $month", fontWeight = FontWeight.Bold)
                        zones.forEach { zone ->
                            ZoneItem(zone = zone, viewModel = viewModel)
                        }
                    }
                }


                    Button(
                        onClick = { viewModel.saveChanges(clientId) },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Guardar cambios")
                    }

            }
        }
    )
}

@Composable
fun ZoneItem(zone: ZoneDepilate, viewModel: ZonesViewModel) {
    var intensity by remember { mutableStateOf(zone.intense) }

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
            Text(text = "Intensidad: $intensity", style = MaterialTheme.typography.body1)
        }

        Row(
            modifier = Modifier
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    intensity++
                    zone.intense = intensity
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Aumentar Intensidad")
            }

            IconButton(
                onClick = {
                    if (intensity > 0) {
                        intensity--
                        zone.intense = intensity
                    }
                }
            ) {
                Icon(Icons.Default.Remove, contentDescription = "Reducir Intensidad")
            }
        }
    }
}


