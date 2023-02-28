package com.example.depilationapp.data.model

data class Client(
    var name: String = "",
    var surname: String = "",
    var document: Int? = null,
    var province: String? = null,
    var numberPhonePersonal: Long = 0,
    var numberPhoneOther: Long = 0,
    var state: Boolean = false,
    var observation: String = "",
    var listZoneRetoque: String? = null,
    var Zone: String? = null,
)



