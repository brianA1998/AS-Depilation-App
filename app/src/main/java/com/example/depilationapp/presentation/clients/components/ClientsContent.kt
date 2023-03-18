package com.example.depilationapp.presentation.clients.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.depilationapp.data.model.Client
import com.example.depilationapp.presentation.navigation.Screen

@Composable
fun ClientsContent(clients: List<Client>, navController: NavHostController) {
    LazyColumn {
        items(items = clients) { client ->
            ClientCard(client) {
                navController.navigate(Screen.DetailScreen.createRoute(clientName = client.name))
            }
        }
    }
}

@Composable
fun ClientCard(client: Client, onClick: () -> Unit) {
    Text(
        text = client.name,
        color = Color.Black,
        fontSize = 24.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    )
}

@Composable
fun DetailScreen(clientName: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Nombre del cliente: $clientName")
    }
}
