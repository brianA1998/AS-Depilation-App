package com.example.depilationapp.domain.repository

import com.example.depilationapp.data.model.ZoneDepilate
import com.example.depilationapp.data.util.Response
import kotlinx.coroutines.flow.Flow

//alias
typealias Zones = List<ZoneDepilate>
typealias ZonesResponse = Response<Zones>


interface ZonesRepository {
    fun getZonesFromFirestore(): Flow<Response<List<ZoneDepilate>>>
    fun getZonesFromFirestoreByClient(clientId: String):  Flow<ZonesResponse>
    suspend fun saveZone(zone: ZoneDepilate)
}