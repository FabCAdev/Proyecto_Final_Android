package com.example.proyecto_final.ui.screens.home

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
 * ViewModel del catálogo principal (HomeScreen).
 *
 * Centraliza la lógica de carga del listado de videojuegos y expone
 * un [StateFlow] reactivo con el estado actual (Loading, Success, Error)
 * para que la vista solo observe y reaccione de forma pasiva.
 */
class HomeViewModel : ViewModel() {

    private val repository = GameRepository()

    private val _uiState = MutableStateFlow<UiState<List<GameResponse>>>(UiState.Loading)
    /** Estado observable del catálogo de juegos. */
    val uiState: StateFlow<UiState<List<GameResponse>>> = _uiState.asStateFlow()

    init {
        loadGames()
    }

    /**
     * Carga la lista completa de videojuegos desde la API.
     * Ejecuta la petición en una corrutina para no bloquear el hilo principal.
     */
    fun loadGames() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val games = repository.getGames()
                _uiState.value = UiState.Success(games)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    e.message ?: "Error al cargar el catálogo de videojuegos"
                )
            }
        }
    }

    /** Reintenta la carga después de un error. */
    fun retry() = loadGames()
}
