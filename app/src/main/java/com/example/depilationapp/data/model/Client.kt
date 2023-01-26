package com.example.depilationapp.data.model

data class Client(
    var name: String,
    var surname: String,
    val document: Int,
    var province: Province,
    var numberPhonePersonal: Long,
    var numberPhoneOther: Long = 0,
    var state: Boolean = false,
    var observation: String = "",
    var listZoneRetoque: List<Zone>,
    var Zone: List<ZoneDepilate>,
)



