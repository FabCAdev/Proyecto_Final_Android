package com.example.proyecto_final.data.repository

import com.example.proyecto_final.data.model.AuthResponse
import com.example.proyecto_final.data.model.GameResponse
import com.example.proyecto_final.data.model.LoginRequest
import com.example.proyecto_final.data.model.MessageResponse
import com.example.proyecto_final.data.network.RetrofitClient
import com.example.proyecto_final.data.network.SessionManager

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
        val response = apiService.login(request)
        // Guardamos el token para las peticiones autenticadas (Jugar Después).
        SessionManager.token = response.token
        return response
    }

    /**
     * Obtiene la lista de juegos marcados como "Jugar Después" del usuario.
     * Requiere una sesión activa (token JWT).
     */
    suspend fun getLaterGames(): List<GameResponse> {
        return apiService.getLater(SessionManager.bearer)
    }

    /**
     * Agrega un juego a la lista "Jugar Después".
     * @param gameId Identificador (_id) del juego.
     */
    suspend fun addToLater(gameId: String): MessageResponse {
        return apiService.addToLater(SessionManager.bearer, gameId)
    }

    /**
     * Elimina un juego de la lista "Jugar Después".
     * @param gameId Identificador (_id) del juego.
     */
    suspend fun removeFromLater(gameId: String): MessageResponse {
        return apiService.removeFromLater(SessionManager.bearer, gameId)
    }
}
