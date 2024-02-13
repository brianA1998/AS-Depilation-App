package com.example.depilationapp.presentation.clients.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.model.Localidad
import com.example.depilationapp.data.model.ZoneDepilate
import com.example.depilationapp.domain.use_case.UseCases
import com.example.depilationapp.presentation.navigation.Screen
import com.example.depilationapp.presentation.zones.ZonesViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID



@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun EditClientScreen(navController: NavHostController, useCases: UseCases, client: Client,viewModel : ZonesViewModel) {
    val (name, setName) = remember { mutableStateOf(client.name) }
    val (surname, setSurname) = remember { mutableStateOf(client.surname) }
    val (document, setDocument) = remember { mutableStateOf(client.document.toString()) }
    var selectedLocalidad by remember { mutableStateOf(client.localidad) }
    var expanded by remember { mutableStateOf(false) }
    val (numberPhonePersonal, setNumberPhonePersonal) = remember { mutableStateOf(client.numberPhonePersonal.toString()) }
    val (numberPhoneOther, setNumberPhoneOther) = remember { mutableStateOf(client.numberPhoneOther.toString()) }
    val (observation, setObservation) = remember { mutableStateOf(client.observation) }
    val coroutineScope = rememberCoroutineScope()
    var zonesDepilated = remember { mutableStateListOf<ZoneDepilate>() }
    var showDialog by remember { mutableStateOf(false) }
    var showUpdateIntensitiesScreen by remember { mutableStateOf(false) }



    // Variables for the dialog inputs
    var dialogIntensity by remember { mutableStateOf(1f) }
    var dialogZone by remember { mutableStateOf("") }
    val zonesEdit = listOf("BRAZO", "ESPALDA", "PIERNAS", "CARA", "CAVADO")


    LaunchedEffect(client.id){
        viewModel.getGroupedZones(client.id)
    }
    val groupedZones = viewModel.groupedZones.value


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
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally){

                    OutlinedTextField(
                        value = name,
                        onValueChange = setName,
                        label = { Text("Nombre") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                    )

                    OutlinedTextField(
                        value = surname,
                        onValueChange = setSurname,
                        label = { Text("Apellido") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
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
                            .padding(bottom = 4.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(8.dp)
                    )

                    OutlinedTextField(
                        value = selectedLocalidad?.province.orEmpty(),
                        onValueChange = { /* Evitar modificaciones */ },
                        label = { Text("Localidad") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp)
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
                            .padding(bottom = 4.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(8.dp)
                    )



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
                            .padding(bottom = 4.dp),
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


                // Display list of zones to depilate
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)){
                    LazyColumn(modifier = Modifier.fillMaxWidth()
                    ) {
                        items(zonesDepilated) { zoneDepilate ->
                            Text(
                                text = "${zoneDepilate.zone}, intensidad: ${zoneDepilate.intense}",
                                Modifier.padding(bottom = 4.dp)
                            )
                        }
                    }
                }


                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF8AB68B)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .height(35.dp)
                        .clip(RoundedCornerShape(50.dp))
                ) {
                    Text(
                        text = "Añadir zona de depilación",
                        color = Color.White,
                        style = MaterialTheme.typography.button.copy(fontSize = 14.sp)
                    )
                }

                Button(
                    onClick = {
                        navController.navigate(
                            Screen.IntensityZoneScreen.createRoute(client.id, zonesDepilated)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF8AB68B)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .height(35.dp)
                        .clip(RoundedCornerShape(25.dp))
                ) {
                    Text(text = "Actualizar Intensidades",
                        color = Color.White,
                        style = MaterialTheme.typography.button)
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

                                // Filtra las zonas disponibles para agregar
                                val availableZones = zonesEdit.filter { zoneEdit ->
                                    !groupedZones
                                        .flatMap { it.value.values }
                                        .flatten()
                                        .any { it.zone == zoneEdit }
                                }
                                ZoneDropdownEdit(dialogZone,availableZones) { newZone -> dialogZone = newZone }
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
                                            clientId = "",
                                            zone = dialogZone,
                                            intense = dialogIntensity.toInt(),
                                            date = System.currentTimeMillis()
                                        )
                                        zonesDepilated.add(newZoneDepilate)
                                        showDialog = false
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
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF409944)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(45.dp)
                        .clip(RoundedCornerShape(30.dp))
                ) {
                    Text(
                        text = "Guardar cambios",
                        color = Color.White,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                }
            }

        }
    }
}

@Composable
fun ZoneDropdownEdit(selectedZone: String, availableZones: List<String>, setZone: (String) -> Unit) {
    val expanded = remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selectedZone,
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
            availableZones.forEach { zoneItem ->
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