package com.example.depilationapp.presentation.clients.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.depilationapp.data.util.Response
import com.example.depilationapp.presentation.clients.ClientsViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ClientsScreen(navController: NavHostController, viewModel: ClientsViewModel = hiltViewModel()) {
    val clientsResponse = viewModel.clientsResponse

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lista de clientes") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Navegar a la pantalla de agregar un nuevo cliente */ },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar cliente")
            }
        },
        content = {
            when (clientsResponse) {
                is Response.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
                is Response.Success -> ClientsContent(
                    clients = clientsResponse.data,
                    navController = navController
                )
                is Response.Failure -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = clientsResponse.e?.message ?: "Error desconocido")
                    }
                }
            }
        }
    )
}

