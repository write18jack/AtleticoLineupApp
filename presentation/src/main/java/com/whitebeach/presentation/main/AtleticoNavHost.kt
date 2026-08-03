package com.whitebeach.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.whitebeach.presentation.matches.MatchesScreen
import com.whitebeach.presentation.players.PlayersScreen

@Composable
fun AtleticoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = MainDestination.PLAYERS.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(
            route = MainDestination.PLAYERS.route,
        ) {
            PlayersScreen()
        }

        composable(
            route = MainDestination.MATCHES.route,
        ) {
            MatchesScreen()
        }
    }
}