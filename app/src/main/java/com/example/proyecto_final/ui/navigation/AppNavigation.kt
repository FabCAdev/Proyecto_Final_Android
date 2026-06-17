package com.example.proyecto_final.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.proyecto_final.ui.profile.ProfileScreen
import com.example.proyecto_final.ui.screens.detail.DetailScreen
import com.example.proyecto_final.ui.screens.home.HomeScreen
import com.example.proyecto_final.ui.screens.later.LaterScreen
import com.example.proyecto_final.ui.screens.login.LoginScreen
import com.example.proyecto_final.ui.search.SearchScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = LoginDestination
    ) {
        composable<LoginDestination> {
            LoginScreen(
                onLoginSuccess = {
                    // Navega al catálogo de juegos quitando el Login de la pila trasera
                    navController.navigate(HomeDestination) {
                        popUpTo(LoginDestination) { inclusive = true }
                    }
                }
            )
        }

        composable<HomeDestination> {
            HomeScreen(
                onNavigateToDetail = { gameName ->
                    navController.navigate(DetailDestination(gameName = gameName))
                },
                onNavigateToSearch = { navController.navigate(SearchDestination) },
                onNavigateToProfile = { navController.navigate(ProfileDestination) },
                onNavigateToLater = { navController.navigate(LaterDestination) }
            )
        }

        composable<DetailDestination> { backStackEntry ->
            val args = backStackEntry.toRoute<DetailDestination>()

            DetailScreen(
                gameName = args.gameName,
                onNavigateBack = {
                    navController.popBackStack() // Regresa de manera segura a la HomeScreen
                }
            )
        }

        composable<SearchDestination> {
            SearchScreen(
                onNavigateToDetail = { gameName ->
                    // Si el usuario encuentra el juego y lo presiona, lo manda directo al detalle
                    navController.navigate(DetailDestination(gameName = gameName))
                },
                onNavigateBack = {
                    // Regresa al Home
                    navController.popBackStack()
                }
            )
        }

        composable<LaterDestination> {
            LaterScreen(
                onNavigateToDetail = { gameName ->
                    navController.navigate(DetailDestination(gameName = gameName))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<ProfileDestination> {
            ProfileScreen(
                onLogout = {
                    // Regresa al Login limpiando todo el historial para que no pueda volver con el botón "Atrás"
                    navController.navigate(LoginDestination) {
                        popUpTo(HomeDestination) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    // Regresa al catálogo de juegos
                    navController.popBackStack()
                }
            )
        }
    }
}