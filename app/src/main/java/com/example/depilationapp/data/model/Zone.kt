package com.example.depilationapp.data.model

import androidx.compose.ui.text.toUpperCase
import java.util.*

enum class Zone(val zone: String) {
    NONE("NINGUNA"),
    LEGS("PIERNAS"),
    ARMS("BRAZOS"),
    DUG("CAVADO"),
    DOWN("BOZO"),
    MUSTACHE("BIGOTE"),
    SIDEBURN("PATILLA"),
    BACK("ESPALDA"),
    ARMPITS("AXILAS"),
    CHEST("PECHO");

    companion object {
        fun safeValueOf(value: String): Zone? {
            return try {
                values().find{ it.zone.equals(value, ignoreCase = true) }
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}



