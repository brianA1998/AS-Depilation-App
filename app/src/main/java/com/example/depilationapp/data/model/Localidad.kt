package com.example.depilationapp.data.model

enum class Localidad(val province: String) {
    NONE("NINGUNA"),
    LA_PAZ("La Paz"),
    VILLA_MERCEDES("Villa Mercedes");

    companion object {
        fun safeValueOf(value: String): Localidad? {
            return try {
                values().find { it.province.equals(value, ignoreCase = true) }
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}

