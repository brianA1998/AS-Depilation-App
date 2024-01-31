package com.example.depilationapp.domain.use_case

import com.example.depilationapp.data.util.Response
import com.example.depilationapp.domain.repository.ZonesRepository
import javax.inject.Inject

class UpdateZoneIntensity @Inject constructor(private val zonesRepository: ZonesRepository) {
    suspend operator fun invoke(zoneId: String, intensity: Int) {
        zonesRepository.updateZoneIntensity(zoneId, intensity)
    }
}
