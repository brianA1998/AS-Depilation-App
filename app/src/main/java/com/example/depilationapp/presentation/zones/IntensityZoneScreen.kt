package com.example.depilationapp.presentation.zones

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun IntensityZoneScreen(navController: NavHostController, clientId: String, zonesString: String, viewModel: ZonesViewModel) {
    LaunchedEffect(clientId) {
        viewModel.getGroupedZones(clientId)
    }
    val groupedZones = viewModel.groupedZones.value


    Log.w("IntensityZoneScreen",groupedZones.toString())

}

