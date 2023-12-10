import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.depilationapp.data.model.ZoneDepilate
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Composable
fun EditIntensityZoneScreen(navController: NavHostController, navBackStackEntry: NavBackStackEntry) {
    val clientId = remember { mutableStateOf("") }
    val zonesString = remember { mutableStateOf("") }
    val zones = remember { mutableStateOf<List<ZoneDepilate>>(emptyList()) }

    val route = navBackStackEntry.arguments?.getString("clientId")
    val zonesRoute = navBackStackEntry.arguments?.getString("zones")

    // Manejar posibles problemas al obtener valores de la ruta
    if (!route.isNullOrBlank() && !zonesRoute.isNullOrBlank()) {
        clientId.value = Uri.decode(route)
        zonesString.value = Uri.decode(zonesRoute)

        // Tratar de analizar la cadena de zonas
        try {
            zones.value = Json.decodeFromString(zonesString.value)
        } catch (e: Exception) {
            // Manejar la excepción de análisis de JSON
            Log.e("EditIntensityZoneScreen", "Error parsing zones JSON: ${e.message}")
        }
    }

    // Resto del código de tu pantalla...
    // Puedes usar las variables `clientId.value` y `zones.value` donde sea necesario.

    // Para verificar si los valores se están leyendo correctamente, puedes imprimirlos
    Log.d("EditIntensityZoneScreen", "Client ID: ${clientId.value}")
    Log.d("EditIntensityZoneScreen", "Zones: ${zones.value}")
}
