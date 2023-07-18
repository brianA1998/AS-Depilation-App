package com.example.depilationapp.presentation.clients.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.depilationapp.data.model.Client
import com.example.depilationapp.presentation.navigation.Screen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun ClientsContent(clients: List<Client>, navController: NavHostController) {
    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = clients) { client ->
            ClientCard(client) {
                val jsonClient = Json.encodeToString(client)
                navController.navigate(Screen.DetailScreen.createRoute(jsonClient)) {

                }
            }
        }
    }
}

@Composable
fun ClientCard(client: Client, onClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(
                    text = client.name,
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                )
                Text(
                    text = "De : ${client.province}",
                    style = MaterialTheme.typography.body2,
                )

            }
        }
    }
}


