package com.example.depilationapp.core

import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.model.Localidad
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ClientExtensionTest {

    @Test
    fun mapToClient_returnsClientWithExpectedData() {
        val data = mapOf(
            "id" to "a123",
            "name" to "Juan",
            "surname" to "Pérez",
            "document" to 12345678,
            "province" to "La Paz",
            "numberPhonePersonal" to 123456789,
            "numberPhoneOther" to 987654321,
            "state" to true,
            "observation" to "Observación"
        )

        val expectedClient = Client(
            id = "a123",
            name = "Juan",
            surname = "Pérez",
            document = 12345678,
            localidad = Localidad.safeValueOf("La Paz"),
            numberPhonePersonal = 123456789,
            numberPhoneOther = 987654321,
            state = true,
            observation = "Observación"
        )

        val client = mapToClient(data)

        assertEquals(expectedClient, client)
    }

    @Test
    fun mapToClient_handlesMissingData() {
        val data = mapOf(
            "id" to "a123",
            "name" to "Juan",
            // El campo 'surname' está ausente
            "document" to 12345678,
            // El campo 'province' es nulo
            "numberPhonePersonal" to 123456789
        )

        val expectedClient = Client(
            id = "a123",
            name = "Juan",
            surname = "",
            document = 12345678,
            localidad = Localidad.NONE,
            numberPhonePersonal = 123456789,
            numberPhoneOther = 0,
            state = true,
            observation = ""
        )

        val client = mapToClient(data)

        assertEquals(expectedClient, client)
    }

    @Test
    fun mapToClient_handlesInvalidData() {
        val data = mapOf(
            "id" to "a123",
            "name" to "Juan",
            "surname" to "Pérez",
            "document" to "invalid", // Valor no válido
            "province" to "La Paz",
            "numberPhonePersonal" to "not_a_number" // Valor no válido
        )

        val expectedClient = Client(
            id = "a123",
            name = "Juan",
            surname = "Pérez",
            document = 0, // Debería establecerse en el valor predeterminado
            localidad = Localidad.safeValueOf("La Paz"),
            numberPhonePersonal = 0,// Debería establecerse en el valor predeterminado
            state = true
        )

        val client = mapToClient(data)

        assertEquals(expectedClient, client)
    }

    @Test
    fun mapToClient_handlesBooleanValues() {
        val data = mapOf(
            "id" to "a123",
            "name" to "Juan",
            "surname" to "Pérez",
            "document" to 12345678,
            "province" to "La Paz",
            "numberPhonePersonal" to 123456789,
            "state" to false // Valor booleano
        )

        val expectedClient = Client(
            id = "a123",
            name = "Juan",
            surname = "Pérez",
            document = 12345678,
            localidad = Localidad.safeValueOf("La Paz"),
            numberPhonePersonal = 123456789,
            state = false,
        )

        val client = mapToClient(data)

        assertEquals(expectedClient, client)
    }


}
