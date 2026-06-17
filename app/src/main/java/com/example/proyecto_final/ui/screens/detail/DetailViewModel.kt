package com.example.proyecto_final.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final.data.model.GameResponse
import com.example.proyecto_final.data.repository.GameRepository
import com.example.proyecto_final.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel de la pantalla de detalle de un videojuego (DetailScreen).
 *
 * Busca un juego específico por nombre y expone su estado reactivo.
 * Soporta reintento en caso de fallo de conexión.
 */
class DetailViewModel : ViewModel() {

    private val repository = GameRepository()

    private val _uiState = MutableStateFlow<UiState<GameResponse>>(UiState.Loading)
    /** Estado observable del detalle del juego. */
    val uiState: StateFlow<UiState<GameResponse>> = _uiState.asStateFlow()

    /** Nombre del juego que se está consultando (para reintentos). */
    private var currentGameName: String = ""

    /**
     * Carga los detalles completos de un videojuego por su nombre.
     * @param gameName Nombre del juego a buscar en la API.
     */
    fun loadGameDetail(gameName: String) {
        currentGameName = gameName
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val game = repository.getGameByName(gameName)
                if (game != null) {
                    _uiState.value = UiState.Success(game)
                } else {
                    _uiState.value = UiState.Error(
                        "No se encontró la información de este videojuego."
                    )
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    e.message ?: "Error al obtener detalles del videojuego"
                )
            }
        }
    }

    /** Reintenta la carga del último juego consultado. */
    fun retry() {
        if (currentGameName.isNotEmpty()) {
            loadGameDetail(currentGameName)
        }
    }
}
