package com.example.proyecto_final.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class AuthResponse(
    val token: String,
    val usuario: UserInfo
)

@Serializable
data class UserInfo(
    val id: String,
    val username: String
)