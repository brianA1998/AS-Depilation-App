package com.example.depilationapp.presentation.clients.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.model.ZoneDepilate
import com.example.depilationapp.data.util.Response
import com.example.depilationapp.presentation.navigation.Screen
import com.example.depilationapp.presentation.zones.ZonesViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun DetailScreen(client: Client, viewModel: ZonesViewModel, navController : NavController) {
    viewModel.getZones(client.id)
    val zonesResponse = viewModel.zonesResponse.value
    Scaffold(
        content = {
            Box(modifier = Modifier.fillMaxSize()){
                Column {

                    Spacer(modifier = Modifier.padding(10.dp))
                    DetailItem(title = "Nombre", value = client.name,modifier = Modifier.fillMaxWidth())
                    DetailItem(title = "Apellido", value = client.surname,modifier = Modifier.fillMaxWidth())
                    DetailItem(title = "Documento", value = client.document?.toString() ?: "",modifier = Modifier.fillMaxWidth())
                    DetailItem(title = "Localidad", value = client.localidad?.province ?: "",modifier = Modifier.fillMaxWidth())
                    DetailItem(
                        title = "Teléfono personal",
                        value = client.numberPhonePersonal?.toString() ?: "",
                        modifier = Modifier.fillMaxWidth()
                    )
                    DetailItem(
                        title = "Teléfono adicional",
                        value = client.numberPhoneOther?.toString() ?: "",
                        modifier = Modifier.fillMaxWidth()
                    )
                    DetailItem(title = "Estado", value = if (client.state) "Activo" else "Inactivo",modifier = Modifier.fillMaxWidth())
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Zonas",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                    when (zonesResponse) {
                        is Response.Loading -> Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                        is Response.Failure -> {
                            // Muestra el error
                            val exception = (zonesResponse as Response.Failure).e
                            Text("Error: ${exception?.message}")
                        }
                        is Response.Success<*> -> {
                            // Muestra las zonas recuperadas
                            val zones = (zonesResponse as Response.Success<List<ZoneDepilate>>).data
                            val zoneNamesAndIntense = zones.flatMap { zone ->
                                listOf(Pair(zone.zone, zone.intense))
                            }.distinct()

                            zoneNamesAndIntense.forEach { (zone, intense) ->

                                DetailItemZone(zone = zone, intensity = intense)
                            }
                        }
                    }

                    DetailItem(title = "Observaciones", value = client.observation,modifier = Modifier.fillMaxWidth())

                    Button(
                        onClick = {
                            val jsonClient = Json.encodeToString(client) // Serializar el cliente a JSON
                            navController.navigate(Screen.EditClientScreen.createRoute(jsonClient))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text("Editar datos del cliente")
                    }

                    Button(
                        onClick = {
                            val route = Screen.HistoricZoneScreen.createRoute(client.id)
                            navController.navigate(route)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "Ver histórico de zonas")
                    }
                }
            }
        }
    )
}

@Composable
fun DetailItem(title: String, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.width(180.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = value,
            fontSize = 18.sp
        )
    }
}

@Composable
fun DetailItemZone(zone: String, intensity: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = zone,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = intensity.toString(),
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

