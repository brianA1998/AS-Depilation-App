package com.example.depilationapp.domain.use_case

data class UseCases(
    val getClients: GetClients,
    val saveClient: SaveClient,
    val getZones: GetZones,
    val saveZone: SaveZone,
    val updateZoneIntensity: UpdateZoneIntensity
)
