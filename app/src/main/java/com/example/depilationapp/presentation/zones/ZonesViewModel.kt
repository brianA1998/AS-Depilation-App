package com.example.depilationapp.presentation.zones

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.depilationapp.data.util.Response.*
import com.example.depilationapp.data.util.Response
import com.example.depilationapp.domain.repository.ZonesResponse
import com.example.depilationapp.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
}