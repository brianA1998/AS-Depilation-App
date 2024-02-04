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

    fun saveChanges(clientId: String) {
        Log.w("ZonesViewModel","entre saveChanges")
        viewModelScope.launch {
            try {
                groupedZones.value.forEach { (_, months) ->
                    months.forEach { (_, zones) ->
                        zones.forEach { zone ->
                            useCases.updateZoneIntensity(zone.id, zone.intense)
                        }
                    }
                }
                getGroupedZones(clientId)
            } catch (e: Exception) {
                Log.e("ZonesViewModel", "Failed to save changes: $e")
            }
        }
    }


}
