package com.example.depilationapp.data.model

enum class Zone(val zone: String) {
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
                valueOf(value.toUpperCase())
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}



