package com.example.depilationapp.domain.repository

import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.model.ZoneDepilate
import com.example.depilationapp.data.util.Response
import kotlinx.coroutines.flow.Flow


//Alias
typealias Clients = List<Client>
typealias ClientsResponse = Response<Clients>


interface ClientsRepository {
    fun getClientsFromFirestore(): Flow<Response<out MutableList<Client>>>
    suspend fun saveClient(client: Client, zones: List<ZoneDepilate>)
}
