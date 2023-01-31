package com.example.depilationapp.presentation.clients.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.depilationapp.domain.repository.Clients

@Composable
fun ClientsContent(
    padding: PaddingValues,
    clients: Clients
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        items(items = clients) { client ->
            ClientCard(client = client)
        }
    }
}