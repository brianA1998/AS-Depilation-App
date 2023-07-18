package com.example.depilationapp.presentation.clients.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.model.Province
import com.example.depilationapp.data.model.Zone
import com.example.depilationapp.data.model.ZoneDepilate
import com.example.depilationapp.domain.use_case.UseCases
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddClientScreen(navController: NavHostController, useCases: UseCases) {
    val (name, setName) = remember { mutableStateOf("") }
    val (surname, setSurname) = remember { mutableStateOf("") }
    val (document, setDocument) = remember { mutableStateOf("") }
    var intensity by remember { mutableStateOf("") }
    var selectedProvince by remember { mutableStateOf(Province.NONE) }
    var expanded by remember { mutableStateOf(false) }
    val (numberPhonePersonal, setNumberPhonePersonal) = remember { mutableStateOf("") }
    val (numberPhoneOther, setNumberPhoneOther) = remember { mutableStateOf("") }
    val (observation, setObservation) = remember { mutableStateOf("") }
    val (zone, setZone) = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var zonesDepilated = remember { mutableStateListOf<ZoneDepilate>() }
    var showDialog by remember { mutableStateOf(false) }

    // Variables for the dialog inputs
    var dialogIntensity by remember { mutableStateOf(1f) }
    var dialogZone by remember { mutableStateOf("") }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar un nuevo cliente") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = " Atrás")
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
            ) {

                Box(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = setName,
                        label = { Text("Nombre") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                    )
                }

                Box(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = surname,
                        onValueChange = setSurname,
                        label = { Text("Apellido") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        shape = RoundedCornerShape(8.dp)
                    )
                }

                Box(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = document,
                        onValueChange = { newInput ->
                            if (newInput.all { it.isDigit() }) {
                                setDocument(newInput)
                            }
                        },
                        label = { Text("Documento") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(8.dp)
                    )
                }

                Box(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = selectedProvince.province,
                        onValueChange = { /* Evitar modificaciones */ },
                        label = { Text("Provincia") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .onFocusChanged {
                                expanded = it.isFocused
                            },
                        shape = RoundedCornerShape(8.dp),
                        readOnly = true, // Asegurarse de que el usuario no pueda escribir manualmente
                        trailingIcon = {
                            Icon(
                                Icons.Filled.ArrowDropDown,
                                contentDescription = null,
                                Modifier.clickable { expanded = !expanded }
                            )
                        },
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        Province.values().forEach { province ->
                            DropdownMenuItem(onClick = {
                                selectedProvince = province
                                expanded = false
                            }) {
                                Text(province.province)
                            }
                        }
                    }
                }

                Box(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = numberPhonePersonal,
                        onValueChange = { newInput ->
                            if (newInput.all { it.isDigit() }) {
                                setNumberPhonePersonal(newInput)
                            }
                        },
                        label = { Text("Teléfono personal") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(8.dp)
                    )
                }

                Box(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = numberPhoneOther,
                        onValueChange = { newInput ->
                            if (newInput.all { it.isDigit() }) {
                                setNumberPhoneOther(newInput)
                            }
                        },
                        label = { Text("Teléfono alternativo") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(8.dp)
                    )
                }

                Box(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = observation,
                        onValueChange = setObservation,
                        label = { Text("Observaciones") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        shape = RoundedCornerShape(8.dp)
                    )
                }

                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                ) {
                    Text("Añadir zona de depilación")
                }

                if (showDialog) {
                    Dialog(onDismissRequest = { showDialog = false }) {
                        Surface(shape = RoundedCornerShape(8.dp)) {
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    "Añadir zona de depilación",
                                    style = MaterialTheme.typography.h6,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                ZoneDropdown(dialogZone) { newZone -> dialogZone = newZone }
                                Text(
                                    "Intensidad de depilación",
                                    style = MaterialTheme.typography.subtitle1,
                                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                                )
                                Slider(
                                    value = dialogIntensity,
                                    onValueChange = { newIntensity ->
                                        dialogIntensity = newIntensity
                                    },
                                    valueRange = 1f..18f,
                                    steps = 17,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                // Displaying selected intensity
                                Text(
                                    text = "Intensidad seleccionada: ${dialogIntensity.toInt()}",
                                    style = MaterialTheme.typography.body2,
                                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                                )
                                Button(
                                    onClick = {
                                        val newZoneDepilate = ZoneDepilate(
                                            id = UUID.randomUUID().toString(),
                                            clientId = "", // We'll set this later when creating the client
                                            zone = dialogZone,
                                            intense = dialogIntensity.toInt(), // Converting intensity to Int
                                            date = System.currentTimeMillis()
                                        )
                                        zonesDepilated.add(newZoneDepilate)
                                        showDialog = false // Close the dialog
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
                                ) {
                                    Text("Guardar zona de depilación")
                                }
                            }
                        }
                    }
                }

                // Display list of zones to depilate
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    zonesDepilated.forEach { zoneDepilate ->
                        Text(
                            text = "${zoneDepilate.zone}, intensidad: ${zoneDepilate.intense}",
                            Modifier.padding(bottom = 4.dp)
                        )
                    }
                }


                Button(
                    onClick = {
                        coroutineScope.launch {
                            val clientId = UUID.randomUUID().toString()
                            val client = Client(
                                id = clientId,
                                name = name,
                                surname = surname,
                                document = document.toIntOrNull(),
                                province = selectedProvince,
                                numberPhonePersonal = numberPhonePersonal.toLongOrNull() ?: 0,
                                numberPhoneOther = numberPhoneOther.toLongOrNull() ?: 0,
                                state = false,
                                observation = observation,
                                listZoneRetoque = "",
                                zoneDepilate = zonesDepilated.map { it.copy(clientId = clientId) }, // Update clientId in ZoneDepilate objects

                            )

                            useCases.saveClient(client)

                            zonesDepilated.forEach { zoneDepilate ->
                                useCases.saveZone(zoneDepilate.copy(clientId = clientId)) // Update clientId in ZoneDepilate objects
                            }
                            navController.navigate("clients")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Guardar cliente")
                }
            }

        }
    }



}


val zones = listOf("BRAZO", "ESPALDA", "PIERNAS", "CARA", "CAVADO")

@Composable
fun ZoneDropdown(zone: String, setZone: (String) -> Unit) {
    val expanded = remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = zone,
            onValueChange = { },
            label = { Text("Zona") },
            readOnly = true,
            trailingIcon = {
                Icon(
                    Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    Modifier.clickable { expanded.value = !expanded.value }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            zones.forEach { zoneItem ->
                DropdownMenuItem(onClick = {
                    setZone(zoneItem)
                    expanded.value = false
                }) {
                    Text(text = zoneItem)
                }
            }
        }
    }
}