package com.cemerlang.ataraxia.data.models.auth

data class LoginResponse(
    val `data`: Data,
    val token: String
)