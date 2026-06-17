package com.example.proyecto_final.data.model

import kotlinx.serialization.Serializable

/**
 * Respuesta genérica de la API para operaciones que sólo devuelven un mensaje
 * (por ejemplo, agregar o quitar un juego de "Jugar Después").
 */
@Serializable
data class MessageResponse(
    val mensaje: String
)
