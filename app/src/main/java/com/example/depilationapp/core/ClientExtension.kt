package com.example.depilationapp.core

import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.model.Zone

fun Client.toMap(): Map<String, Any> {
    return mapOf(
        "id" to id,
        "name" to name,
        "surname" to surname,
        "document" to (document ?: ""),
        "province" to (province ?: ""),
        "numberPhonePersonal" to numberPhonePersonal,
        "numberPhoneOther" to numberPhoneOther,
        "state" to state,
        "observation" to observation,
        "listZoneRetoque" to (listZoneRetoque ?: ""),
        "zone" to (zone?.zone ?: "")
    )
}

fun mapToClient(data: Map<String, Any>): Client {
    val id = data["id"] as? String ?: ""
    val name = data["name"] as? String ?: ""
    val surname = data["surname"] as? String ?: ""
    val document = data["document"] as? Int?
    val province = data["province"] as? String?
    val numberPhonePersonal = data["numberPhonePersonal"] as? Long ?: 0L
    val numberPhoneOther = data["numberPhoneOther"] as? Long ?: 0L
    val state = data["state"] as? Boolean ?: false
    val observation = data["observation"] as? String ?: ""
    val listZoneRetoque = data["listZoneRetoque"] as? String?
    val zone = Zone.values().firstOrNull { it.zone == data["zone"] as? String } ?: Zone.ARMS

    return Client(
        id, name, surname, document, province, numberPhonePersonal,
        numberPhoneOther, state, observation, listZoneRetoque, zone
    )
}



