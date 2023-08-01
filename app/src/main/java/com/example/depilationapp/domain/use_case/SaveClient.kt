package com.example.depilationapp.domain.use_case

import com.example.depilationapp.data.model.Client
import com.example.depilationapp.domain.repository.ClientsRepository

class SaveClient(private val repo: ClientsRepository) {
    suspend operator fun invoke(client: Client) = repo.saveClient(client)
}