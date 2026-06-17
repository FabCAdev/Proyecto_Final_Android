package com.example.proyecto_final.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final.data.model.AuthResponse
import com.example.proyecto_final.data.model.LoginRequest
import com.example.proyecto_final.data.repository.GameRepository
import com.example.proyecto_final.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel de la pantalla de autenticación (LoginScreen).
 *
 * Gestiona el ciclo de vida del proceso de login:
 * - null = estado inicial (formulario visible, sin acción en curso)
 * - Loading = petición de login en progreso
 * - Success = autenticación exitosa con datos del usuario
 * - Error = credenciales incorrectas o fallo de conexión
 */
class LoginViewModel : ViewModel() {

    private val repository = GameRepository()

    private val _loginState = MutableStateFlow<UiState<AuthResponse>?>(null)
    /**
     * Estado observable del proceso de login.
     * `null` indica que no se ha iniciado ninguna acción (formulario limpio).
     */
    val loginState: StateFlow<UiState<AuthResponse>?> = _loginState.asStateFlow()

    /**
     * Ejecuta el proceso de autenticación contra la API.
     * @param username Nombre de usuario ingresado.
     * @param password Contraseña ingresada.
     */
    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            try {
                val response = repository.login(
                    LoginRequest(username = username, password = password)
                )
                _loginState.value = UiState.Success(response)
            } catch (e: Exception) {
                _loginState.value = UiState.Error(
                    e.message ?: "Error: Usuario o contraseña incorrectos"
                )
            }
        }
    }

    /**
     * Reinicia el estado del login al estado inicial (formulario limpio).
     * Útil para volver a mostrar el formulario después de un error.
     */
    fun resetState() {
        _loginState.value = null
    }
}
