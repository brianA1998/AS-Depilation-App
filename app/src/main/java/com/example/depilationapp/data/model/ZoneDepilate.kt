package com.example.depilationapp.data.model


import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.listSerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class ZoneDepilate(
    var id: String = "",
    var clientId: String = "",
    val zone: String = "",
    val intense: Int = 0,
    val date: Long = System.currentTimeMillis()
)

fun ZoneDepilate.toMap(): Map<String, Any> {
    return mapOf(
        "id" to id,
        "clientId" to clientId,
        "zone" to zone,
        "intense" to intense,
        "date" to date
    )
}

