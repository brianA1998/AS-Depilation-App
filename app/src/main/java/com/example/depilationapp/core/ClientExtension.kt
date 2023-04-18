package com.example.depilationapp.core

import androidx.compose.ui.text.toUpperCase
import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.model.Zone
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.modules.SerializersModule

fun mapToClient(data: Map<String, Any>): Client {
    return Client(
        id = data["id"] as? String ?: "",
        name = data["name"] as String ?: "",
        surname = data["surname"] as String ?: "",
        document = (data["document"] as? Number)?.toInt() ?: 0,
        province = data["province"] as String? ?: "",
        numberPhonePersonal = (data["numberPhonePersonal"] as? Number)?.toLong() ?: 0,
        numberPhoneOther = (data["numberPhoneOther"] as? Number)?.toLong() ?: 0,
        state = data["state"] as? Boolean ?: true,
        observation = data["observation"] as? String ?: "",
        listZoneRetoque = data["listZoneRetoque"] as? String ?: "",
        zone = (data["zone"] as String?)?.let { Zone.safeValueOf(it) },
    )
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

val serializersModule = SerializersModule {
    contextual(Zone::class, ZoneSerializer)
}
