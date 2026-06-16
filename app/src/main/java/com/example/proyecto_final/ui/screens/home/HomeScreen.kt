package com.example.proyecto_final.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage // ← Importamos Coil para cargar imágenes desde internet
import com.example.proyecto_final.data.model.GameResponse
import com.example.proyecto_final.data.network.RetrofitClient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetail: (String) -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    // Estados para almacenar los juegos descargados de la API y el control de carga
    var listaJuegos by remember { mutableStateOf<List<GameResponse>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val context = LocalContext.current

    // LaunchedEffect ejecuta código asíncrono automáticamente al mostrar la pantalla
    LaunchedEffect(Unit) {
        try {
            // Llamamos al endpoint GET /api/games de tu backend en Render
            val juegosDesdeApi = RetrofitClient.instance.getGames()
            listaJuegos = juegosDesdeApi
            isLoading = false
        } catch (e: Exception) {
            isLoading = false
            Toast.makeText(context, "Error al cargar el catálogo de videojuegos", Toast.LENGTH_LONG).show()
        }
    }

    // Obtenemos los géneros únicos de los juegos que llegaron del backend de forma dinámica
    val generos = listaJuegos.map { it.genre }.distinct()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Game Store") },
                actions = {
                    IconButton(onClick = onNavigateToSearch) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
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
            if (isLoading) {
                // Rueda de progreso mientras se descargan los videojuegos de MongoDB
                CircularProgressIndicator()
            } else if (listaJuegos.isEmpty()) {
                Text(text = "No hay videojuegos disponibles en la tienda.")
            } else {
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
                                            // Reemplazamos el Box de texto fijo por una imagen de internet real
                                            AsyncImage(
                                                model = juego.image,
                                                contentDescription = "Portada de ${juego.name}",
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(110.dp),
                                                contentScale = ContentScale.Crop // Ajusta la imagen al contenedor de forma estética
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
    }
}