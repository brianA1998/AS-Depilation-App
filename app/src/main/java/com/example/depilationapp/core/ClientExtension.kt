package com.example.depilationapp.core

import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.model.Localidad
import com.example.depilationapp.data.model.ZoneDepilate

fun mapToClient(data: Map<String, Any>): Client {


    return Client(
        id = data["id"] as? String ?: "",
        name = data["name"] as? String ?: "",
        surname = data["surname"] as? String ?: "",
        document = (data["document"] as? Number)?.toInt() ?: 0,
        localidad = Localidad.safeValueOf(data["province"] as String),
        numberPhonePersonal = (data["numberPhonePersonal"] as? Number)?.toLong() ?: 0,
        numberPhoneOther = (data["numberPhoneOther"] as? Number)?.toLong() ?: 0,
        state = data["state"] as? Boolean ?: true,
        observation = data["observation"] as? String ?: "",
    )
}