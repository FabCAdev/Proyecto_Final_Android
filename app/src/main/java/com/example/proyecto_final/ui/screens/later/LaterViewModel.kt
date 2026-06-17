package com.example.proyecto_final.ui.screens.later

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
 * ViewModel de la pantalla "Jugar Después" (LaterScreen).
 *
 * Consume el endpoint autenticado `GET /api/later` y expone el estado reactivo
 * (Loading, Success, Error). También permite quitar juegos de la lista,
 * recargando el listado tras una eliminación exitosa.
 */
class LaterViewModel : ViewModel() {

    private val repository = GameRepository()

    private val _uiState = MutableStateFlow<UiState<List<GameResponse>>>(UiState.Loading)
    /** Estado observable de la lista de juegos guardados. */
    val uiState: StateFlow<UiState<List<GameResponse>>> = _uiState.asStateFlow()

    init {
        loadLater()
    }

    /** Carga la lista "Jugar Después" del usuario autenticado. */
    fun loadLater() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val games = repository.getLaterGames()
                _uiState.value = UiState.Success(games)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    e.message ?: "Error al cargar tu lista de Jugar Después"
                )
            }
        }
    }

    /**
     * Quita un juego de la lista y recarga el listado.
     * @param gameId Identificador (_id) del juego a eliminar.
     */
    fun removeGame(gameId: String) {
        viewModelScope.launch {
            try {
                repository.removeFromLater(gameId)
                loadLater()
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    e.message ?: "No se pudo quitar el juego de la lista"
                )
            }
        }
    }

    /** Reintenta la carga después de un error. */
    fun retry() = loadLater()
}
