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

@Serializable(with = ZoneDepilate.ZoneDepilateSerializer::class)
data class ZoneDepilate(
    var listZone: List<Zone>? = null,
    var intense: Int = 0,
) {
    object ZoneDepilateSerializer : KSerializer<ZoneDepilate?> {
        @OptIn(ExperimentalSerializationApi::class)
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("ZoneDepilate") {
                element("listZone", listSerialDescriptor(Zone.ZoneSerializer.descriptor))
                element("intense", Int.serializer().descriptor)
            }

        override fun serialize(encoder: Encoder, value: ZoneDepilate?) {
            val compositeEncoder = encoder.beginStructure(descriptor)
            if (value != null) {
                compositeEncoder.encodeSerializableElement(descriptor, 0, ListSerializer(Zone.ZoneSerializer), value.listZone ?: emptyList())
                compositeEncoder.encodeIntElement(descriptor, 1, value.intense)
            }
            compositeEncoder.endStructure(descriptor)
        }

        override fun deserialize(decoder: Decoder): ZoneDepilate? {
            val compositeDecoder = decoder.beginStructure(descriptor)
            var listZone: List<Zone>? = null
            var intense = 0

            loop@ while (true) {
                when (val index = compositeDecoder.decodeElementIndex(descriptor)) {
                    0 -> listZone = compositeDecoder.decodeSerializableElement(descriptor, 0, ListSerializer(
                        Zone.ZoneSerializer
                    ))
                    1 -> intense = compositeDecoder.decodeIntElement(descriptor, 1)
                    CompositeDecoder.DECODE_DONE -> break@loop
                    else -> throw SerializationException("Unknown index $index")
                }
            }
            compositeDecoder.endStructure(descriptor)
            return ZoneDepilate(listZone, intense)
        }
    }
}