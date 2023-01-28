package com.example.depilationapp.data.repository.datasource

import com.example.depilationapp.data.model.Client
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ClientRemoteDataSource {
    suspend fun getAllClients(): Response<Client>
}