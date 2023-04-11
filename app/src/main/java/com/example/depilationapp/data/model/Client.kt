package com.example.depilationapp.data.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor

@Serializable
data class Client(
    val id: String = "a123",
    var name: String = "",
    var surname: String = "",
    var document: Int? = null,
    var province: String? = null,
    var numberPhonePersonal: Long = 0,
    var numberPhoneOther: Long = 0,
    var state: Boolean = false,
    var observation: String = "",
    var listZoneRetoque: String? = null,
    @Serializable(with = ZoneSerializer::class)
    var zone: Zone? = null,
)

@Serializer(forClass = Zone::class)
object ZoneSerializer : KSerializer<Zone?> {
    override fun serialize(encoder: Encoder, value: Zone?) {
        encoder.encodeString(value?.zone ?: "")
    }

    override fun deserialize(decoder: Decoder): Zone? {
        val stringValue = decoder.decodeString()
        return Zone.values().firstOrNull { it.zone == stringValue }
    }

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Zone", PrimitiveKind.STRING)
}
