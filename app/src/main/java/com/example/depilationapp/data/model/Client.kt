package com.example.depilationapp.data.model


import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Client(
    var id: String = "a123",
    var name: String = "",
    var surname: String = "",
    var document: Int? = null,
    var localidad: Localidad? = null,
    var numberPhonePersonal: Long = 0,
    var numberPhoneOther: Long = 0,
    var state: Boolean = false,
    var observation: String = "",
    @Contextual var zoneDepilate: List<ZoneDepilate> = listOf(),
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
        "province" to (localidad?.province ?: ""),
        "numberPhonePersonal" to numberPhonePersonal,
        "numberPhoneOther" to numberPhoneOther,
        "state" to state,
        "observation" to observation,
        "zoneDepilate" to zoneDepilateList
    )
}
