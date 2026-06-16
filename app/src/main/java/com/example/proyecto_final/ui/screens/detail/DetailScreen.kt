package com.example.proyecto_final.ui.screens.detail

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.proyecto_final.data.model.GameResponse
import com.example.proyecto_final.data.network.RetrofitClient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    gameName: String,
    onNavigateBack: () -> Unit
) {
    var juego by remember { mutableStateOf<GameResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    // Buscamos los detalles completos del juego en el backend usando su nombre
    // Nota: Si en el futuro prefieres pasar el ID por navegación, el proceso es idéntico
    LaunchedEffect(gameName) {
        try {
            val todosLosJuegos = RetrofitClient.instance.getGames()
            // Buscamos el juego que coincida con el nombre seleccionado
            juego = todosLosJuegos.find { it.name == gameName }
            isLoading = false
        } catch (e: Exception) {
            isLoading = false
            Toast.makeText(context, "Error al obtener detalles del videojuego", Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = juego?.name ?: "Detalles") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (juego == null) {
                Text(text = "No se encontró la información de este videojuego.")
            } else {
                val game = juego!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Portada grande del juego
                    AsyncImage(
                        model = game.image,
                        contentDescription = "Portada de ${game.name}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp),
                        contentScale = ContentScale.Crop
                    )

                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = game.name,
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Etiqueta del Género
                        SuggestionChip(
                            onClick = { },
                            label = { Text(game.genre.replaceFirstChar { it.uppercase() }) }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Precio",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = "$${game.price}",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Descripción",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (game.description.isBlank()) "Este videojuego no tiene una descripción disponible actualmente." else game.description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}