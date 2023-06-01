package com.example.depilationapp.core

import android.util.Log
import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.model.Province
import com.example.depilationapp.data.model.Zone
import com.example.depilationapp.data.model.ZoneDepilate

fun mapToClient(data: Map<String, Any>): Client {

    val zoneData = (data["zone"] as? List<Map<String, Any>>) ?: listOf()

    val listZone = zoneData.map { zoneMap ->
        val zoneName = zoneMap["zone"] as? String ?: ""
        val zoneIntensity = zoneMap["intense"] as? Int ?: 0
        Zone(zoneName, zoneIntensity)
    }


    val listZoneRetoque = (data["zone_retoque"] as? List<String>) ?: listOf()


    Log.d("zoneDepilate", "zoneDepilate: $zoneDepilate")

    return Client(
        id = data["id"] as? String ?: "",
        name = data["name"] as String ?: "",
        surname = data["surname"] as String ?: "",
        document = (data["document"] as? Number)?.toInt() ?: 0,
        province = Province.safeValueOf(data["province"] as String),
        numberPhonePersonal = (data["number_phone"] as? Number)?.toLong() ?: 0,
        numberPhoneOther = (data["number_phone_other"] as? Number)?.toLong() ?: 0,
        state = data["state"] as? Boolean ?: true,
        observation = data["observation"] as? String ?: "",
        listZoneRetoque = listZoneRetoque,
        zoneDepilate = zoneDepilate,
    )
}