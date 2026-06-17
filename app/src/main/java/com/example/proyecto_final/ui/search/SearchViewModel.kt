package com.example.proyecto_final.ui.search

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
 * ViewModel de la pantalla de búsqueda (SearchScreen).
 *
 * Carga la lista completa de juegos al inicializarse y expone:
 * - [uiState]: estado de la carga de datos (Loading/Success/Error).
 * - [query]: texto de búsqueda actual para filtrado local.
 */
class SearchViewModel : ViewModel() {

    private val repository = GameRepository()

    private val _uiState = MutableStateFlow<UiState<List<GameResponse>>>(UiState.Loading)
    /** Estado observable de la lista de juegos disponibles para búsqueda. */
    val uiState: StateFlow<UiState<List<GameResponse>>> = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")
    /** Texto de búsqueda reactivo. */
    val query: StateFlow<String> = _query.asStateFlow()

    init {
        loadGames()
    }

    /**
     * Carga la lista completa de videojuegos para el buscador.
     */
    fun loadGames() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val games = repository.getGames()
                _uiState.value = UiState.Success(games)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    e.message ?: "Error al sincronizar el buscador"
                )
            }
        }
    }

    /**
     * Actualiza el texto de búsqueda.
     * El filtrado se realiza localmente en la vista usando este valor.
     */
    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    /** Limpia el campo de búsqueda. */
    fun clearQuery() {
        _query.value = ""
    }

    /** Reintenta la carga después de un error. */
    fun retry() = loadGames()
}
