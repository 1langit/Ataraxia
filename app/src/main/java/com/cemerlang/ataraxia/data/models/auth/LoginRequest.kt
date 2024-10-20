package com.cemerlang.ataraxia.data.models.auth

data class LoginRequest(
    val email: String,
    val password: String
)