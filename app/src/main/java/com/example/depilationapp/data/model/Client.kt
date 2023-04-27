package com.example.depilationapp.data.model

import com.example.depilationapp.core.ZoneSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Client(
    val id: String = "a123",
    var name: String = "",
    var surname: String = "",
    var document: Int? = null,
    var province: Province? = null,
    var numberPhonePersonal: Long = 0,
    var numberPhoneOther: Long = 0,
    var state: Boolean = false,
    var observation: String = "",
    var listZoneRetoque: String? = null,
    @Serializable(with = ZoneSerializer::class) var zone: Zone? = null,
    @Serializable(with = ZoneDepilate.ZoneDepilateSerializer::class) var zoneDepilate: ZoneDepilate? = null
)

fun Client.toMap(): Map<String, Any> {
    return mapOf(
        "id" to id,
        "name" to name,
        "surname" to surname,
        "document" to (document ?: ""),
        "province" to (province?.province ?: ""),
        "numberPhonePersonal" to numberPhonePersonal,
        "numberPhoneOther" to numberPhoneOther,
        "state" to state,
        "observation" to observation,
        "listZoneRetoque" to (listZoneRetoque ?: ""),
        "zone" to (zone?.zone ?: ""),
        "zoneDepilate" to (ZoneDepilate ?: ZoneDepilate())
    )
}