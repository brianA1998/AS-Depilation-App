package com.example.depilationapp.data.model

import com.example.depilationapp.core.ZoneSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.listSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = ZoneDepilate.ZoneDepilateSerializer::class)
data class ZoneDepilate(
    var listZone : List<Zone> ? = null,
    var intense : Int = 0,
){
    object ZoneDepilateSerializer : KSerializer<ZoneDepilate?> {
        @OptIn(ExperimentalSerializationApi::class)
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("ZoneDepilate") {
                element("listZone", listSerialDescriptor(ZoneSerializer.descriptor))
                element("intense", Int.serializer().descriptor)
            }

        override fun serialize(encoder: Encoder, value: ZoneDepilate?) {
            // Implement the serialization logic for ZoneDepilate
        }

        override fun deserialize(decoder: Decoder): ZoneDepilate? {
            // Implement the deserialization logic for ZoneDepilate
        }
    }
}