package com.cemerlang.ataraxia.data.models.dashboard

data class UserDataRequest(
    val birthday: String,
    val gender: String,
    val health_condition: List<String>? = null
)