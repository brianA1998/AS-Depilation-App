package com.example.depilationapp.presentation.clients.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.model.ZoneDepilate
import com.example.depilationapp.data.util.*
import com.example.depilationapp.presentation.zones.ZonesViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(client: Client, viewModel: ZonesViewModel) {
    viewModel.getZones(client.id)
    val zonesResponse = viewModel.zonesResponse
    Log.d("zonasClient", "Client: $zonesResponse")
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalle de : ${client.name}") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Log.d("DetailScreen", "Client: $client")

                Spacer(modifier = Modifier.padding(10.dp))
                DetailItem(title = "Nombre", value = client.name)
                DetailItem(title = "Apellido", value = client.surname)
                DetailItem(title = "Documento", value = client.document?.toString() ?: "")
                DetailItem(title = "Provincia", value = client.province?.province ?: "")
                DetailItem(
                    title = "Teléfono personal",
                    value = client.numberPhonePersonal?.toString() ?: ""
                )
                DetailItem(
                    title = "Teléfono adicional",
                    value = client.numberPhoneOther?.toString() ?: ""
                )
                DetailItem(title = "Estado", value = if (client.state) "Activo" else "Inactivo")
                DetailItem(title = "Zona de Retoque", value = client.listZoneRetoque ?: "")
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

                DetailItem(title = "Observaciones", value = client.observation)
            }
        }
    )
}

@Composable
fun DetailItem(title: String, value: String) {
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

