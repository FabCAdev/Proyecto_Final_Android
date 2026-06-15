package com.example.proyecto_final.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.proyectofinal.ui.screens.home.HomeScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination
    ) {
        // 1. PANTALLA PRINCIPAL (Tu Pantalla)
        composable<HomeDestination> {
            HomeScreen(
                onNavigateToDetail = { gameName ->
                    navController.navigate(DetailDestination(gameName = gameName))
                },
                onNavigateToSearch = { navController.navigate(SearchDestination) },
                onNavigateToProfile = { navController.navigate(ProfileDestination) }
            )
        }

        // 2. PANTALLA DETALLE (Para tu compañero)
        composable<DetailDestination> { backStackEntry ->
            val args = backStackEntry.toRoute<DetailDestination>()
            // Tu compañero usará args.gameName para buscar en la API
        }

        // 3. PANTALLA BÚSQUEDA / FILTROS (Para tu compañero)
        composable<SearchDestination> {
            // SearchScreen()
        }

        // 4. PANTALLA PERFIL / CONFIGURACIÓN (Para tu compañero)
        composable<ProfileDestination> {
            // ProfileScreen()
        }
    }
}