package com.example.depilationapp.data.model


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
    var zoneDepilate: ZoneDepilate? = null,
)

fun Client.toMap(): Map<String, Any> {
    val zoneDepilateList = this.zoneDepilate?.listZone?.map { zone ->
        mapOf("name" to zone.name, "intensity" to zone.intensity)
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
