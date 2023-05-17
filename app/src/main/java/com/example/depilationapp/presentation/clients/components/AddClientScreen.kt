package com.example.depilationapp.presentation.clients.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.model.Province
import com.example.depilationapp.data.model.Zone
import com.example.depilationapp.data.model.ZoneDepilate
import com.example.depilationapp.domain.use_case.UseCases
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddClientScreen(navController: NavHostController, useCases: UseCases) {
    val (name, setName) = remember { mutableStateOf("") }
    val (surname, setSurname) = remember { mutableStateOf("") }
    val (document, setDocument) = remember { mutableStateOf("") }
    val (intensity, setIntensity) = remember { mutableStateOf("") }
    val (province, setProvince) = remember { mutableStateOf("") }
    val (numberPhonePersonal, setNumberPhonePersonal) = remember { mutableStateOf("") }
    val (numberPhoneOther, setNumberPhoneOther) = remember { mutableStateOf("") }
    val (observation, setObservation) = remember { mutableStateOf("") }
    val (zone, setZone) = remember { mutableStateOf(Zone.NONE) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar un nuevo cliente") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = setName,
                label = { Text("Nombre") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(8.dp),
            )

            OutlinedTextField(
                value = surname,
                onValueChange = setSurname,
                label = { Text("Apellido") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(8.dp)
            )

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

            OutlinedTextField(
                value = province,
                onValueChange = setProvince,
                label = { Text("Provincia") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(8.dp)
            )


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


            OutlinedTextField(
                value = observation,
                onValueChange = setObservation,
                label = { Text("Observaciones") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(8.dp)
            )

            OutlinedTextField(
                value = intensity,
                onValueChange = { newInput ->
                    if (newInput.all { it.isDigit() }) {
                        setIntensity(newInput)
                    }
                },
                label = { Text("Intensidad de depilación") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(8.dp)
            )

            ZoneDropdown(zone, setZone)

            Button(
                onClick = {
                    coroutineScope.launch {
                        val client = Client(
                            name = name,
                            surname = surname,
                            document = document.toIntOrNull(),
                            province = Province.safeValueOf(province) ?: Province.NONE,
                            numberPhonePersonal = numberPhonePersonal.toLongOrNull() ?: 0,
                            numberPhoneOther = numberPhoneOther.toLongOrNull() ?: 0,
                            state = false,
                            observation = observation,
                            listZoneRetoque = null,
                            zoneDepilate = ZoneDepilate(
                                listZone = if (zone != Zone.NONE) listOf(zone) else null,
                                intense = intensity.toIntOrNull() ?: 0
                            )
                        )
                        Log.d("Number_Client", "Number personal: ${client.numberPhonePersonal}")
                        Log.d("Number_Client", "Number other: ${client.numberPhoneOther}")
                        useCases.saveClient(client)
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

@Composable
fun ZoneDropdown(zone: Zone, setZone: (Zone) -> Unit) {
    val expanded = remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = zone.zone,
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
            Zone.values().forEach { zoneItem ->
                DropdownMenuItem(onClick = {
                    setZone(zoneItem)
                    expanded.value = false
                }) {
                    Text(text = zoneItem.zone)
                }
            }
        }
    }

}



