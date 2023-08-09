package com.example.depilationapp.presentation.zones

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.depilationapp.data.model.ZoneDepilate
import com.example.depilationapp.data.util.Response
import com.example.depilationapp.data.util.Response.*
import com.example.depilationapp.domain.repository.ZonesResponse
import com.example.depilationapp.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ZonesViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    var zonesResponse by mutableStateOf<ZonesResponse>(Loading)
    val groupedZones = mutableStateOf<Map<Int, Map<Int, List<ZoneDepilate>>>>(emptyMap())

    fun getZones(clientId: String) = viewModelScope.launch {
        useCases.getZones(clientId).collect { response ->
            zonesResponse = response
        }
    }

    fun getGroupedZones(clientId: String) {
        viewModelScope.launch {
            useCases.getZones(clientId).collect { zonesResponse ->
                if (zonesResponse is Response.Success<*>) {
                    val zones = zonesResponse.data as List<ZoneDepilate> // Asumiendo que `data` es de tipo `List<ZoneDepilate>`
                    val zonesWithDates = zones.map { zone ->
                        val calendar = Calendar.getInstance().apply {
                            timeInMillis = zone.date
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
                }
            }
        }
    }
}
