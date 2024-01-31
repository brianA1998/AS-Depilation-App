package com.example.depilationapp.presentation.zones

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.depilationapp.data.model.ZoneDepilate
import com.example.depilationapp.data.util.Response
import com.example.depilationapp.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ZonesViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    val zonesResponse: MutableStateFlow<Response<List<ZoneDepilate>>> =
        MutableStateFlow(Response.Loading)

   // val groupedZones: MutableStateMap<Int, MutableStateMap<Int, List<ZoneDepilate>>> =
      //  MutableStateMap()

    val groupedZones: MutableStateFlow<Map<Int, Map<Int, List<ZoneDepilate>>>> =
        MutableStateFlow(emptyMap())

    fun getZones(clientId: String) {
        viewModelScope.launch {
            useCases.getZones(clientId).collect { zonesResponseWrapper ->
                when (zonesResponseWrapper) {
                    is Response.Success<*> -> {
                        val zones = zonesResponseWrapper.data as List<ZoneDepilate>
                        zonesResponse.value = Response.Success(zones)
                    }
                    is Response.Failure -> {
                        zonesResponse.value = Response.Failure(zonesResponseWrapper.e)
                    }
                    Response.Loading -> {
                        zonesResponse.value = Response.Loading
                    }
                }
            }
        }
    }



    fun getGroupedZones(clientId: String) {
        viewModelScope.launch {
            groupedZones.value = emptyMap()
            useCases.getZones(clientId).collect { zonesResponse ->
                if (zonesResponse is Response.Success<*>) {
                    val zones = zonesResponse.data as List<ZoneDepilate>
                    val zonesWithDates = zones.map { zone ->
                        val timestamp = zone.date

                        val calendar = Calendar.getInstance().apply {
                            timeInMillis = timestamp // Asegúrate de que esto esté en milisegundos
                        }
                        val year = calendar.get(Calendar.YEAR)
                        val month = calendar.get(Calendar.MONTH) + 1 // Los meses en Calendar comienzan desde 0
                        Pair(zone, year to month)
                    }
                    val groupedZonesVal = zonesWithDates.groupBy { it.second.first }.mapValues { entry ->
                        entry.value.groupBy { it.second.second }.mapValues { monthEntry ->
                            monthEntry.value.map { it.first }
                        }
                    }
                    groupedZones.value = groupedZonesVal
                    Log.d("ZonesViewModel", "groupedzones: ${groupedZones.value}")
                }
            }
        }
    }

    fun updateIntensity(zoneId: String, intensity: Int) {
        // Aquí realizarías la lógica para actualizar la intensidad de la zona en tu repositorio o fuente de datos
        // Puedes llamar a los casos de uso necesarios para actualizar la intensidad de la zona en la base de datos o en otro lugar
        viewModelScope.launch {
            try {
                // Supongamos que tienes un caso de uso llamado updateZoneIntensity que recibe el ID de la zona y la nueva intensidad
                useCases.updateZoneIntensity(zoneId, intensity)
                // Si la actualización tiene éxito, no necesitas manejar nada aquí, ya que el estado se actualiza automáticamente
            } catch (e: Exception) {
                // Si ocurre un error, puedes manejarlo lanzando una excepción
                // Aquí podrías mostrar un mensaje de error o realizar cualquier otra acción necesaria
                Log.e("ZonesViewModel", "Failed to update intensity: $e")
            }
        }
    }
}
