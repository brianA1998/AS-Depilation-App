package com.example.depilationapp.domain.use_case

import com.example.depilationapp.domain.repository.ClientsRepository

class GetClients(
    private val repo: ClientsRepository
) {
    operator fun invoke() = repo.getClientsFromFirestore()

}