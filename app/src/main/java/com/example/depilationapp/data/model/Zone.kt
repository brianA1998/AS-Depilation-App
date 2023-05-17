package com.example.depilationapp.data.model

import androidx.compose.ui.text.toUpperCase
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

@kotlinx.serialization.Serializable
data class Zone(
    var name : String = "",
    var intensity : Int = 0
)



