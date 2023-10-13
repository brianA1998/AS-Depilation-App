package com.example.depilationapp.presentation.clients.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.model.Localidad
import com.example.depilationapp.data.model.ZoneDepilate
import com.example.depilationapp.domain.use_case.UseCases
import kotlinx.coroutines.launch
import java.util.UUID

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditClientScreen(navController: NavHostController, useCases: UseCases, client: Client) {
    val (name, setName) = remember { mutableStateOf(client.name) }
    val (surname, setSurname) = remember { mutableStateOf(client.surname) }
    val (document, setDocument) = remember { mutableStateOf(client.document.toString()) }
    var intensity by remember { mutableStateOf("") }
    var selectedLocalidad by remember { mutableStateOf(client.localidad) }
    var expanded by remember { mutableStateOf(false) }
    val (numberPhonePersonal, setNumberPhonePersonal) = remember { mutableStateOf(client.numberPhonePersonal.toString()) }
    val (numberPhoneOther, setNumberPhoneOther) = remember { mutableStateOf(client.numberPhoneOther.toString()) }
    val (observation, setObservation) = remember { mutableStateOf(client.observation) }
    val (zone, setZone) = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var zonesDepilated = remember { mutableStateListOf<ZoneDepilate>() }
    var showDialog by remember { mutableStateOf(false) }

    // Variables for the dialog inputs
    var dialogIntensity by remember { mutableStateOf(1f) }
    var dialogZone by remember { mutableStateOf("") }

    Log.d("EditClientScreen", "Client: $client")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar datos de cliente") },
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
                        value = selectedLocalidad?.province.orEmpty(),
                        onValueChange = { /* Evitar modificaciones */ },
                        label = { Text("Localidad") },
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
                        Localidad.values().forEach { province ->
                            DropdownMenuItem(onClick = {
                                selectedLocalidad = province
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
                        value = numberPhoneOther.toString(),
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
                                ZoneDropdownEdit(dialogZone) { newZone -> dialogZone = newZone }
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

                            val client = Client(
                                id = client.id,
                                name = name,
                                surname = surname,
                                document = document.toIntOrNull() ?: 0,
                                localidad = selectedLocalidad,
                                numberPhonePersonal = numberPhonePersonal.toLongOrNull() ?: 0,
                                numberPhoneOther = numberPhoneOther.toLongOrNull() ?: 0,
                                state = false,
                                observation = observation,
                            )

                            // Actualiza los objetos ZoneDepilate con la ID correcta del cliente
                            zonesDepilated.forEach { zoneDepilate ->
                                zoneDepilate.clientId = client.id
                            }

                            // Pasa el cliente y la lista de zonas depiladas al método saveClient
                            useCases.saveClient(client, zonesDepilated)

                            navController.navigate("clients")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Guardar cambios")
                }
            }

        }
    }
}

val zonesEdit = listOf("BRAZO", "ESPALDA", "PIERNAS", "CARA", "CAVADO")

@Composable
fun ZoneDropdownEdit(zone: String, setZone: (String) -> Unit) {
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
            zonesEdit.forEach { zoneItem ->
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