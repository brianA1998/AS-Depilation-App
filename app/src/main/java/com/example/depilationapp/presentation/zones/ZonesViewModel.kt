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

    fun getZones(clientId: String) = viewModelScope.launch {
        useCases.getZones(clientId).collect { response ->
            zonesResponse = response
        }
    }

    val groupedZones: MutableState<Map<Int, Map<Int, List<ZoneDepilate>>>> = mutableStateOf(emptyMap())

    fun getGroupedZones(clientId: String) {
        val liveData = useCases.getZones(clientId)

        liveData.observeForever { zonesResponse ->
            if (zonesResponse is Response.Success) {
                val zones = zonesResponse.data
                val zonesWithDates = zones.map { zone ->
                    val calendar = Calendar.getInstance().apply {
                        timeInMillis = zone.date
                    }
                    val date = LocalDate.of(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                    Pair(zone, date)
                }
                val groupedZonesVal = zonesWithDates.groupBy { it.second.year }.mapValues { entry ->
                    entry.value.groupBy { it.second.monthValue }.mapValues { monthEntry ->
                        monthEntry.value.map { it.first }
                    }
                }
                groupedZones.value = groupedZonesVal
            }
        }
    }





}