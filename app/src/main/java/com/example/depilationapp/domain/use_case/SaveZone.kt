package com.example.depilationapp.domain.use_case

import com.example.depilationapp.data.model.ZoneDepilate
import com.example.depilationapp.domain.repository.ZonesRepository

class SaveZone(private val zonesRepository : ZonesRepository) {
    suspend operator fun invoke(zone: ZoneDepilate) = zonesRepository.saveZone(zone)
}