package com.example.depilationapp.data.model

import androidx.compose.ui.text.toUpperCase
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
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
                values().find { it.zone.equals(value, ignoreCase = true) }
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }

    @Serializer(forClass = Zone::class)
    object ZoneSerializer : KSerializer<Zone> {
        override val descriptor = PrimitiveSerialDescriptor("Zone", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: Zone) {
            encoder.encodeString(value.name)
        }

        override fun deserialize(decoder: Decoder): Zone {
            return Zone.valueOf(decoder.decodeString())
        }
    }

}



