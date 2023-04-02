package com.example.depilationapp.presentation.clients.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.depilationapp.data.model.Client

@Composable
fun DetailScreen(client: Client) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Nombre del cliente: ${client.name}")
        Log.i("detalle","$client")
    }
}