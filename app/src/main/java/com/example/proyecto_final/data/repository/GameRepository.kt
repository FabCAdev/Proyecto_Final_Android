package com.example.proyecto_final.data.repository

import com.example.proyecto_final.data.model.AuthResponse
import com.example.proyecto_final.data.model.GameResponse
import com.example.proyecto_final.data.model.LoginRequest
import com.example.proyecto_final.data.network.RetrofitClient

/**
 * Repositorio que actúa como fuente única de verdad para los datos de la aplicación.
 * Desacopla los ViewModels de la implementación concreta de red (Retrofit),
 * facilitando el testing y la escalabilidad futura.
 */
class GameRepository {

    private val apiService = RetrofitClient.instance

    /**
     * Obtiene la lista completa de videojuegos desde el backend.
     * @param genre Filtro opcional por género.
     * @return Lista de [GameResponse] con los juegos disponibles.
     */
    suspend fun getGames(genre: String? = null): List<GameResponse> {
        return apiService.getGames(genre)
    }

    /**
     * Busca un videojuego específico por su nombre.
     * @param name Nombre exacto del juego a buscar.
     * @return El [GameResponse] encontrado, o null si no existe.
     */
    suspend fun getGameByName(name: String): GameResponse? {
        val allGames = apiService.getGames()
        return allGames.find { it.name == name }
    }

    /**
     * Ejecuta el proceso de autenticación contra el backend.
     * @param request Credenciales del usuario (username + password).
     * @return [AuthResponse] con el token y la información del usuario.
     */
    suspend fun login(request: LoginRequest): AuthResponse {
        return apiService.login(request)
    }
}
