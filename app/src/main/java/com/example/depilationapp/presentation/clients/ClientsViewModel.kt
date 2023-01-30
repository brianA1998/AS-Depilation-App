package com.example.depilationapp.presentation.clients

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.depilationapp.data.util.Response.*
import com.example.depilationapp.domain.repository.ClientsResponse
import com.example.depilationapp.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.depilationapp.data.util.Response.*

@HiltViewModel
class ClientsViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    var clientsResponse by mutableStateOf<ClientsResponse>(Loading)

    init {
        getClients()
    }

    private fun getClients() = viewModelScope.launch {
        useCases.getClients().collect { response ->
            clientsResponse = response
        }
    }
}