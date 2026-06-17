package com.example.proyecto_final.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/**
 * Esquema de color oscuro (look principal tipo tienda de videojuegos).
 */
private val DarkColorScheme = darkColorScheme(
    primary = GamePurple80,
    secondary = GameGrey80,
    tertiary = GamePink80,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurface = DarkOnSurface,
    onSurfaceVariant = DarkOnSurfaceVariant,
    error = GameErrorDark,
    onPrimary = GamePurple20,
    onBackground = DarkOnSurface
)

/**
 * Esquema de color claro, conservando la identidad morada de la app.
 */
private val LightColorScheme = lightColorScheme(
    primary = GamePurple40,
    secondary = GameGrey40,
    tertiary = GamePink40,
    background = LightBackground,
    surface = LightSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurface = LightOnSurface,
    onSurfaceVariant = LightOnSurfaceVariant,
    error = GameError,
    onPrimary = Color.White,
    onBackground = LightOnSurface
)

/**
 * Tema global de la aplicación.
 *
 * Por defecto se desactiva el color dinámico para garantizar que la identidad
 * visual (morado de "Game Store") sea consistente en todos los dispositivos,
 * tal como exige la temática del proyecto.
 */
@Composable
fun Proyecto_FinalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
