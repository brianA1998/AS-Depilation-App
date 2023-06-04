package com.example.depilationapp.domain.use_case

import com.example.depilationapp.data.model.ZoneDepilate
import com.example.depilationapp.data.util.Response
import com.example.depilationapp.domain.repository.ZonesRepository
import com.example.depilationapp.domain.repository.ZonesResponse
import kotlinx.coroutines.flow.Flow

class GetZones(private val zonesRepository: ZonesRepository) {
    operator fun invoke(clientId: String): Flow<ZonesResponse> = zonesRepository.getZonesFromFirestoreByClient(clientId)
}