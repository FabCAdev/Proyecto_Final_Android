package com.example.proyecto_final.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

@Serializable
data class DetailDestination(val gameName: String)

@Serializable
object SearchDestination

@Serializable
object ProfileDestination