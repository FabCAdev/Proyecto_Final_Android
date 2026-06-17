package com.example.proyecto_final.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Paleta de color del sistema de diseño "Game Store".
 *
 * Mantiene la identidad morada con la que se construyó la app, pero la formaliza
 * en una paleta intencional con variantes para tema claro y oscuro, pensada para
 * una tienda de videojuegos (acentos vibrantes sobre superficies neutras).
 */

// --- Marca (morado) ---
val GamePurple80 = Color(0xFFD0BCFF) // Acento claro (tema oscuro)
val GamePurple40 = Color(0xFF6650A4) // Acento principal (tema claro)
val GamePurple20 = Color(0xFF21005D) // Contenedor profundo

// --- Secundarios / terciarios ---
val GameGrey80 = Color(0xFFCCC2DC)
val GameGrey40 = Color(0xFF625B71)
val GamePink80 = Color(0xFFEFB8C8)
val GamePink40 = Color(0xFF7D5260)

// --- Superficies tema claro ---
val LightBackground = Color(0xFFFEF7FF)
val LightSurface = Color(0xFFFEF7FF)
val LightSurfaceVariant = Color(0xFFE7E0EC)
val LightOnSurface = Color(0xFF1C1B1F)
val LightOnSurfaceVariant = Color(0xFF49454F)

// --- Superficies tema oscuro (look "gamer") ---
val DarkBackground = Color(0xFF141218)
val DarkSurface = Color(0xFF1D1B20)
val DarkSurfaceVariant = Color(0xFF49454F)
val DarkOnSurface = Color(0xFFE6E0E9)
val DarkOnSurfaceVariant = Color(0xFFCAC4D0)

// --- Estados ---
val GameError = Color(0xFFB3261E)
val GameErrorDark = Color(0xFFF2B8B5)
