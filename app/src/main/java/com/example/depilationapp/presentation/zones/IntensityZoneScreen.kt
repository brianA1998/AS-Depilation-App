package com.example.depilationapp.presentation.zones

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(showSaveButton) {
        if (showSaveButton) {
            viewModel.saveChanges(clientId)
            snackbarHostState.showSnackbar(
                message = "Se guardó correctamente la intensidad",
                actionLabel = "",
                duration = SnackbarDuration.Short
            )
            showSaveButton = false // Reset showSaveButton after displaying Snackbar
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
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
                    onClick = {
                        viewModel.saveChanges(clientId)
                        showSaveButton = true
                    },
                    modifier = Modifier.align(Alignment.End).padding(top = 16.dp)
                        .height(45.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(30.dp)),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF8AB68B)),
                ) {
                    Text(
                        text = "Guardar cambios",
                        color = Color.White, // Cambiar el color del texto
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                }
            }
        }
    )
}


@Composable
fun SuccessSnackbar(message: String) {
    Snackbar(
        modifier = Modifier.padding(16.dp),
        content = { Text(text = message) },
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


