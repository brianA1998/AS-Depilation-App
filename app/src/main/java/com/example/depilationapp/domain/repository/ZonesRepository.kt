package com.example.depilationapp.domain.repository

import com.example.depilationapp.data.model.ZoneDepilate
import com.example.depilationapp.data.util.Response
import kotlinx.coroutines.flow.Flow

interface ZonesRepository {
    fun getZonesFromFirestore(): Flow<Response<List<ZoneDepilate>>>
    suspend fun saveZone(zone: ZoneDepilate)
}