package com.example.proyecto_final.ui.state

/**
 * Contenedor genérico de estado para la comunicación reactiva entre
 * ViewModels y pantallas (Composables).
 *
 * Modela los tres estados fundamentales de cualquier operación asíncrona:
 * - [Loading]: La operación está en curso.
 * - [Success]: La operación terminó exitosamente con datos de tipo [T].
 * - [Error]: La operación falló con un mensaje descriptivo.
 */
sealed class UiState<out T> {
    /** Estado de carga — la petición a la API se está procesando. */
    object Loading : UiState<Nothing>()

    /** Estado de éxito — contiene los datos recibidos de la API. */
    data class Success<T>(val data: T) : UiState<T>()

    /** Estado de error — contiene un mensaje amigable para el usuario. */
    data class Error(val message: String) : UiState<Nothing>()
}
