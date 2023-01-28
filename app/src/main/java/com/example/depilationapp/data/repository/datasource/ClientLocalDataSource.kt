package com.example.depilationapp.data.repository.datasource

import com.example.depilationapp.data.model.Client
import kotlinx.coroutines.flow.Flow

interface ClientLocalDataSource {
    fun getClientItems(): Flow<List<Client>>
}