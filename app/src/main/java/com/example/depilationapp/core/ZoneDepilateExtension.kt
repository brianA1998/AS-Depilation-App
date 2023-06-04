package com.example.depilationapp.core

import com.example.depilationapp.data.model.ZoneDepilate
fun ZoneDepilate.toMap(): Map<String, Any> {
    return mapOf(
        "id" to this.id,
        "clientId" to this.clientId,
        "zone" to this.zone,
        "intensity" to this.intense,
        "date" to this.date
    )
}

fun mapToZone(data: Map<String, Any>): ZoneDepilate {
    return ZoneDepilate(
        id = data["id"] as? String ?: "",
        clientId = data["clientId"] as? String ?: "",
        zone = data["zone"] as? String ?: "",
        intense = data["intensity"] as? Int ?: 0,
        date = data["date"] as? Long ?: 0L
    )
}
