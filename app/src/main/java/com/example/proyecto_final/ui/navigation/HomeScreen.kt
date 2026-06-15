package com.proyectofinal.ui.navigation

import androidx.compose.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

// Modelo idéntico a la estructura de la base de datos (image_9110aa.png)
data class GameMock(
    val name: String,
    val image: String,
    val price: Double,
    val genre: String,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetail: (String) -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    // Datos mock optimizados según los enums de la API de Node.js
    val listaJuegosPrueba = listOf(
        GameMock("Resident Evil 4", "", 59.99, "miedo", "Terror de supervivencia icónico."),
        GameMock("Silent Hill 2", "", 49.99, "miedo", "Terror psicológico profundo."),
        GameMock("Doom Eternal", "", 39.99, "disparos", "Acción frenética en primera persona."),
        GameMock("Call of Duty", "", 69.99, "disparos", "Multijugador militar intensivo."),
        GameMock("God of War Ragnarok", "", 69.99, "accion", "Aventura épica de Kratos y Atreus."),
        GameMock("Devil May Cry 5", "", 29.99, "accion", "Hack and slash de alta velocidad.")
    )

    val generosApi = listOf("accion", "miedo", "disparos")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Proyecto Final - Tienda") },
                actions = {
                    IconButton(onClick = onNavigateToSearch) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Perfil")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(
                    text = "Catálogo de Videojuegos",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            items(generosApi) { genero ->
                val juegosFiltrados = listaJuegosPrueba.filter { it.genre == genero }
                val tituloSeccion = genero.replaceFirstChar { it.uppercase() }

                Text(
                    text = tituloSeccion,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(juegosFiltrados) { juego ->
                        Card(
                            onClick = { onNavigateToDetail(juego.name) },
                            modifier = Modifier.size(width = 160.dp, height = 210.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Surface(
                                    modifier = Modifier.fillMaxWidth().height(95.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text("[Imagen]", style = MaterialTheme.typography.bodySmall)
                                    }
                                }

                                Text(
                                    text = juego.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    maxLines = 1
                                )

                                Text(
                                    text = "$${juego.price}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}