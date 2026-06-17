package com.example.proyecto_final.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_final.ui.state.UiState

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    // Estado de los campos de texto (estado de UI puro, se mantiene en el composable)
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    // Observamos el estado reactivo del proceso de login
    val loginState by viewModel.loginState.collectAsState()

    // Determinamos si la UI debe estar bloqueada (durante la carga)
    val isLoading = loginState is UiState.Loading

    // Efecto lateral: cuando el login es exitoso, navegamos y mostramos el Toast
    LaunchedEffect(loginState) {
        val state = loginState
        if (state is UiState.Success) {
            Toast.makeText(
                context,
                "¡Bienvenido ${state.data.usuario.username}!",
                Toast.LENGTH_SHORT
            ).show()
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )

        // Mostrar mensaje de error inline si falló el login
        if (loginState is UiState.Error) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = (loginState as UiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (isLoading) {
            // Estado de Carga: indicador circular
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    if (username.isBlank() || password.isBlank()) {
                        Toast.makeText(
                            context,
                            "Por favor llena todos los campos",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    // Delegamos la lógica de autenticación al ViewModel
                    viewModel.login(username, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Ingresar")
            }
        }
    }
}