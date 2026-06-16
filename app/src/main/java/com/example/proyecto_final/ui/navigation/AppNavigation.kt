package com.example.proyecto_final.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.proyecto_final.ui.screens.detail.DetailScreen
import com.example.proyecto_final.ui.screens.home.HomeScreen
import com.example.proyecto_final.ui.screens.login.LoginScreen

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
                onNavigateToProfile = { navController.navigate(ProfileDestination) }
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

        composable<SearchDestination> { }

        composable<ProfileDestination> { }
    }
}