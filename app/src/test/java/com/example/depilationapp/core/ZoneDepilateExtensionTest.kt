package com.example.depilationapp.core

import com.example.depilationapp.data.model.ZoneDepilate
import org.junit.Test
import junit.framework.TestCase.assertEquals

class ZoneDepilateExtensionTest {

    @Test
    fun mapToZone_returnsZoneDepilateWithExpectedData() {
        val data = mapOf(
            "id" to "z123",
            "clientId" to "c456",
            "zone" to "Arms",
            "intense" to 5L,
            "date" to 1630760400000L
        )

        val expectedZone = ZoneDepilate(
            id = "z123",
            clientId = "c456",
            zone = "Arms",
            intense = 5,
            date = 1630760400000L
        )

        val zone = mapToZone(data)

        assertEquals(expectedZone, zone)
    }

}