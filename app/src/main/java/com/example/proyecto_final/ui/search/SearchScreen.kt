package com.example.proyecto_final.ui.search

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.proyecto_final.data.model.GameResponse
import com.example.proyecto_final.data.network.RetrofitClient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateToDetail: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    var listaCompletaJuegos by remember { mutableStateOf<List<GameResponse>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        try {
            listaCompletaJuegos = RetrofitClient.instance.getGames()
            isLoading = false
        } catch (e: Exception) {
            isLoading = false
            Toast.makeText(context, "Error al sincronizar el buscador", Toast.LENGTH_SHORT).show()
        }
    }

    val juegosFiltrados = remember(query, listaCompletaJuegos) {
        if (query.isBlank()) {
            emptyList()
        } else {
            listaCompletaJuegos.filter { juego ->
                juego.name.contains(query, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 3.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically // ← CORREGIDO: Alineación vertical correcta
                ) {
                    IconButton(onClick = onNavigateBack) {
                        // Usamos la versión AutoMirrored para evitar el warning de descontinuación
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }

                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = { Text("Buscar videojuegos...") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                            unfocusedContainerColor = androidx.compose.ui.graphics.Color.Transparent
                        ),
                        singleLine = true,
                        trailingIcon = {
                            if (query.isNotEmpty()) {
                                IconButton(onClick = { query = "" }) {
                                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Limpiar")
                                }
                            }
                        }
                    )
                }
            }
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
            } else if (query.isBlank()) {
                Text(
                    text = "Escribe el nombre de un juego para buscar",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline
                )
            } else if (juegosFiltrados.isEmpty()) {
                Text(
                    text = "No se encontró ninguna coincidencia.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(juegosFiltrados) { juego ->
                        ListItem(
                            headlineContent = { Text(juego.name) },
                            supportingContent = { Text("$${juego.price} — ${juego.genre.replaceFirstChar { it.uppercase() }}") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onNavigateToDetail(juego.name) } // ← CORREGIDO: Evento click universal integrado aquí
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                    }
                }
            }
        }
    }
}