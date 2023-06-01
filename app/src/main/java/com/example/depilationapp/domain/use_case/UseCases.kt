package com.example.depilationapp.domain.use_case

data class UseCases(
    val getClients: GetClients,
    val saveClient: SaveClient,
    private val getZones: GetZones,
    private val saveZone: SaveZone,
)
