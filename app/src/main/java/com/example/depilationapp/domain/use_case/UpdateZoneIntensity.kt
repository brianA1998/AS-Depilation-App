package com.example.depilationapp.domain.use_case

import android.util.Log
import com.example.depilationapp.data.util.Response
import com.example.depilationapp.domain.repository.ZonesRepository
import javax.inject.Inject

class UpdateZoneIntensity @Inject constructor(private val zonesRepository: ZonesRepository) {
    suspend operator fun invoke(zoneId: String, intensity: Int) {
        Log.w("UpdateZoneIntensity","entre al metodo suspend y los valores son: $zoneId y $intensity")
        zonesRepository.updateZoneIntensity(zoneId, intensity)
    }
}
