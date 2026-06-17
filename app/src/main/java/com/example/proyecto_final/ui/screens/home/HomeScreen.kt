package com.example.proyecto_final.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.proyecto_final.data.model.GameResponse
import com.example.proyecto_final.ui.state.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetail: (String) -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToLater: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    // Observamos el estado reactivo del ViewModel de forma pasiva
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Game Store") },
                actions = {
                    IconButton(onClick = onNavigateToSearch) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                    }
                    IconButton(onClick = onNavigateToLater) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "Jugar Después"
                        )
                    }
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Perfil")
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
            // Manejo exhaustivo de los tres estados visuales
            when (val state = uiState) {
                is UiState.Loading -> {
                    // Estado de Carga: indicador circular de progreso
                    CircularProgressIndicator()
                }

                is UiState.Success -> {
                    val listaJuegos = state.data

                    if (listaJuegos.isEmpty()) {
                        Text(text = "No hay videojuegos disponibles en la tienda.")
                    } else {
                        // Obtenemos los géneros únicos de forma dinámica
                        val generos = listaJuegos.map { it.genre }.distinct()

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(generos) { genero ->
                                // Filtramos los juegos pertenecientes a este género específico
                                val juegosFiltrados = listaJuegos.filter { it.genre == genero }

                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = genero.replaceFirstChar { it.uppercase() },
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                    )

                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    ) {
                                        items(juegosFiltrados) { juego ->
                                            Card(
                                                onClick = { onNavigateToDetail(juego.name) },
                                                modifier = Modifier.size(width = 140.dp, height = 190.dp)
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(12.dp),
                                                    verticalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    // Imagen de internet real con Coil
                                                    AsyncImage(
                                                        model = juego.image,
                                                        contentDescription = "Portada de ${juego.name}",
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .height(110.dp),
                                                        contentScale = ContentScale.Crop
                                                    )

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
                }

                is UiState.Error -> {
                    // Estado de Error: mensaje amigable + botón de reintento
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Error",
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.retry() }) {
                            Text("Reintentar")
                        }
                    }
                }
            }
        }
    }
}