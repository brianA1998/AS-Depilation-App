package com.example.depilationapp.data.model


import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Client(
    var id: String = "a123",
    var name: String = "",
    var surname: String = "",
    var document: Int? = null,
    var province: Province? = null,
    var numberPhonePersonal: Long = 0,
    var numberPhoneOther: Long = 0,
    var state: Boolean = false,
    var observation: String = "",
    var listZoneRetoque: String = "",
    @Contextual val zoneDepilate: List<ZoneDepilate> = listOf(),
)

fun Client.toMap(): Map<String, Any> {
    val zoneDepilateList = this.zoneDepilate?.map { zone ->
        mapOf(
            "name" to zone.zone, "intensity" to zone.intense
        )
    } ?: listOf<Map<String, Any>>()

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
        "zone" to zoneDepilateList
    )
}
