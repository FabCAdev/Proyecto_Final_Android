package com.example.proyecto_final.data.network

/**
 * Almacén en memoria del token de sesión (JWT) del usuario autenticado.
 *
 * El token se obtiene al hacer login y se reutiliza en las peticiones que
 * requieren autorización (la lista "Jugar Después"). Se mantiene como objeto
 * singleton para que toda la app comparta la misma sesión activa.
 */
object SessionManager {

    /** Token JWT crudo devuelto por la API tras un login exitoso. */
    var token: String? = null

    /** Header de autorización listo para Retrofit (formato "Bearer <token>"). */
    val bearer: String
        get() = "Bearer ${token.orEmpty()}"

    /** Indica si hay una sesión activa. */
    val isLoggedIn: Boolean
        get() = !token.isNullOrBlank()

    /** Limpia la sesión (logout). */
    fun clear() {
        token = null
    }
}
