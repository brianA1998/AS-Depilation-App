package com.example.depilationapp.presentation.clients.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.depilationapp.data.model.Client


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(client: Client) {
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
                Log.d("DetailScreen", "Client: $client")

                Spacer(modifier = Modifier.padding(10.dp))
                DetailItem(title = "Nombre", value = client.name)
                DetailItem(title = "Apellido", value = client.surname)
                DetailItem(title = "Documento", value = client.document?.toString() ?: "")
                DetailItem(title = "Provincia", value = client.province?.province ?: "")
                DetailItem(
                    title = "Teléfono personal",
                    value = client.numberPhonePersonal?.toString() ?: ""
                )
                DetailItem(title = "Teléfono adicional", value = client.numberPhoneOther?.toString()  ?: "")
                DetailItem(title = "Estado", value = if (client.state) "Activo" else "Inactivo")
                DetailItem(title = "Zona de Retoque", value = client.listZoneRetoque ?: "")
                client.zoneDepilate?.let { zoneDepilate ->
                    zoneDepilate.listZone?.let { listZone ->
                        if (listZone.isNotEmpty()) {
                            Text(
                                text = "Zonas",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                            )
                            listZone.forEach { zone ->
                                DetailItem(
                                    title = zone.name,
                                    value = "Intensidad: ${zoneDepilate.intense}"
                                )
                            }
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