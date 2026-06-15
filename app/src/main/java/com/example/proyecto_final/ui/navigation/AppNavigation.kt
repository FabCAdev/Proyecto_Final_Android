package com.example.proyecto_final.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.proyecto_final.ui.screens.home.HomeScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination
    ) {
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
        }

        composable<SearchDestination> { }

        composable<ProfileDestination> { }
    }
}