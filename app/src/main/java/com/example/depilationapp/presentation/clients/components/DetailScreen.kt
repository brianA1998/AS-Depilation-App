package com.example.depilationapp.presentation.clients.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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


@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(client: Client, viewModel: ZonesViewModel, navController: NavController) {
    viewModel.getZones(client.id)
    val zonesResponse = viewModel.zonesResponse.value

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Información personal
                SectionTitle(title = "Información personal")
                DetailItem(title = "Nombre", value = client.name)
                DetailItem(title = "Apellido", value = client.surname)
                DetailItem(title = "Documento", value = client.document?.toString() ?: "")
                DetailItem(title = "Localidad", value = client.localidad?.province ?: "")
                DetailItem(title = "Teléfono personal", value = client.numberPhonePersonal?.toString() ?: "")
                DetailItem(title = "Teléfono adicional", value = client.numberPhoneOther?.toString() ?: "")

                Spacer(modifier = Modifier.height(24.dp))

                // Zonas de tratamiento
                SectionTitle(title = "Zonas de tratamiento")
                when (zonesResponse) {
                    is Response.Loading -> CircularProgressIndicator()
                    is Response.Failure -> {
                        val exception = (zonesResponse as Response.Failure).e
                        Text("Error: ${exception?.message}")
                    }
                    is Response.Success<*> -> {
                        val zones = (zonesResponse as Response.Success<List<ZoneDepilate>>).data
                        zones.forEach { zone ->
                            DetailItemZone(zone = zone.zone, intensity = zone.intense)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Observaciones
                SectionTitle(title = "Observaciones")
                Text(
                    text = client.observation,
                    modifier = Modifier.padding(end = 16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val jsonClient = Json.encodeToString(client)
                        navController.navigate(Screen.EditClientScreen.createRoute(jsonClient))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White),
                    shape = RoundedCornerShape(12.dp)

                ) {
                    Text("Editar datos del cliente")
                }
                Spacer(modifier = Modifier.height(2.dp))
                Button(
                    onClick = {
                        val route = Screen.HistoricZoneScreen.createRoute(client.id)
                        navController.navigate(route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White),
                    shape = RoundedCornerShape(12.dp)

                ) {
                    Text("Ver histórico de zonas")
                }
            }
        }
    }
}

@Composable
fun DetailItem(title: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$title:",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 16.sp
        )
    }
}

@Composable
fun DetailItemZone(zone: String, intensity: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = zone,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Intensidad: ${intensity.toString()}",
            fontSize = 14.sp,
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

