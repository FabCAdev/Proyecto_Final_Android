package com.example.proyecto_final.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GameResponse(
    val _id: String,
    val name: String,
    val image: String,
    val price: Double,
    val genre: String,
    val description: String = ""
)