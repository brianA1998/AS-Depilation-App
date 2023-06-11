package com.example.depilationapp.core

import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.model.Province
import com.example.depilationapp.data.model.Zone
import com.example.depilationapp.data.model.ZoneDepilate

fun mapToClient(data: Map<String, Any>): Client {

    val zoneData = (data["zone"] as? List<Map<String, Any>>) ?: listOf()
    val zoneDepilateData = (data["zone_depilate"] as? List<Map<String, Any>>) ?: listOf()

    val listZone = zoneData.map { zoneMap ->
        val zoneName = zoneMap["zone"] as? String ?: ""
        val zoneIntensity = zoneMap["intense"] as? Int ?: 0
        Zone(zoneName, zoneIntensity)
    }

    val zoneDepilate = zoneDepilateData.map { zoneDepilateMap ->
        ZoneDepilate(
            id = zoneDepilateMap["id"] as? String ?: "",
            clientId = zoneDepilateMap["clientId"] as? String ?: "",
            zone = zoneDepilateMap["zone"] as? String ?: "",
            intense = zoneDepilateMap["intense"] as? Int ?: 0,
            date = zoneDepilateMap["date"] as? Long ?: 0
        )
    }


    val listZoneRetoque = (data["listZoneRetoque"] as? String ) ?: ""


    return Client(
        id = data["id"] as? String ?: "",
        name = data["name"] as? String ?: "",
        surname = data["surname"] as? String ?: "",
        document = (data["document"] as? Number)?.toInt() ?: 0,
        province = Province.safeValueOf(data["province"] as String),
        numberPhonePersonal = (data["numberPhonePersonal"] as? Number)?.toLong() ?: 0,
        numberPhoneOther = (data["numberPhoneOther"] as? Number)?.toLong() ?: 0,
        state = data["state"] as? Boolean ?: true,
        observation = data["observation"] as? String ?: "",
        listZoneRetoque = listZoneRetoque,
        zoneDepilate = zoneDepilate,
    )
}