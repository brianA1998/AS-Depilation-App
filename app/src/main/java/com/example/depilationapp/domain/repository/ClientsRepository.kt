package com.example.depilationapp.domain.repository

import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.util.Response
import kotlinx.coroutines.flow.Flow


//Alias
typealias Clients = List<Client>
typealias ClientsResponse = Response<Clients>


interface ClientsRepository {
    fun getClientsFromFirestore(): Flow<ClientsResponse>
}