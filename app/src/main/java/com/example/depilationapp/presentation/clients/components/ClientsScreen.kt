package com.example.depilationapp.presentation.clients.components

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.depilationapp.core.Constants
import com.example.depilationapp.data.util.Response
import com.example.depilationapp.presentation.clients.ClientsViewModel
import com.example.depilationapp.presentation.navigation.Screen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ClientsScreen(navController: NavHostController, viewModel: ClientsViewModel = hiltViewModel()) {
    Log.i("MVVM","Estoy en ClientsScreen")
    val clientsResponse = viewModel.clientsResponse

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lista de clientes") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(1f)) {
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
                                Text(text = clientsResponse.e?.message ?: "Ups ocurrio un error")
                            }
                        }
                    }
                }

                FloatingActionButton(
                    onClick = {
                        try {
                            Log.d("CargaCliente", "The onClick function was called")
                            navController.navigate(Screen.AddClientScreen.route)
                        } catch (e: Exception) {
                            Log.e("NavigationError", e.toString())
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(16.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Agregar cliente")
                }

            }
        }
    )
}