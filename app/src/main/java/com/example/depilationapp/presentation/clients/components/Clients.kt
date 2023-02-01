package com.example.depilationapp.presentation.clients.components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.util.Response
import com.example.depilationapp.presentation.clients.ClientsViewModel

@Composable
fun Clients(
    viewModel: ClientsViewModel = hiltViewModel(),
    clientsContent: @Composable (clients: Client) -> Unit
) {
    when (val clientsReponse = viewModel.clientsResponse) {
        is Response.Loading -> ProgressBar()
    }
}