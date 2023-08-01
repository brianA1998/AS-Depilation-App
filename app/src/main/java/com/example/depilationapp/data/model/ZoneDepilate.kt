package com.example.depilationapp.data.model


import kotlinx.serialization.Serializable

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

