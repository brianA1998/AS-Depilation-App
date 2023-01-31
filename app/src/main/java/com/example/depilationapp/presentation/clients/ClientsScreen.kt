package com.example.depilationapp.presentation.clients

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.depilationapp.presentation.clients.components.Books

@Composable
fun ClientsScreen(
    viewModel: ClientsViewModel = hiltViewModel()
) {
    Scaffold(
        content = { padding ->
            Clients

        }
    ) {

    }
}