package com.example.depilationapp.core

import android.util.Log
import com.example.depilationapp.data.model.ZoneDepilate

fun mapToZone(data: Map<String, Any>): ZoneDepilate {
    val zone = ZoneDepilate(
        id = data["id"] as? String ?: "",
        clientId = data["clientId"] as? String ?: "",
        zone = data["zone"] as? String ?: "",
        intense = (data["intense"] as? Long)?.toInt() ?: 0,
        date = data["date"] as? Long ?: 0L
    )

    return zone
}
