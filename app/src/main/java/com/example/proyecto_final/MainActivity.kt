package com.example.proyecto_final

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.proyectofinal.ui.navigation.AppNavigation
import com.proyectofinal.ui.theme.Proyecto_FinalTheme // Asegúrate de que coincida con el nombre de tu tema generado

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Proyecto_FinalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 1. Inicializamos el control de navegación de Jetpack Compose
                    val navController = rememberNavController()

                    // 2. Llamamos a tu mapa de navegación pasando el controlador
                    AppNavigation(navController = navController)
                }
            }
        }
    }
}