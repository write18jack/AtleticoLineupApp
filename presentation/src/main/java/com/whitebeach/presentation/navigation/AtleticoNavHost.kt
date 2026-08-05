package com.whitebeach.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.whitebeach.presentation.matches.MatchesScreen
import com.whitebeach.presentation.players.detail.PlayerDetailScreen
import com.whitebeach.presentation.players.list.PlayersScreen

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
            PlayersScreen(
                onPlayerClick = { playerId ->
                    navController.navigate(
                        PlayerDetailDestination.createRoute(playerId),
                    )
                },
            )
        }

        composable(
            route = MainDestination.MATCHES.route,
        ) {
            MatchesScreen()
        }

        composable(
            route = PlayerDetailDestination.route,
            arguments = listOf(
                navArgument(
                    PlayerDetailDestination.PLAYER_ID_ARGUMENT,
                ) {
                    type = NavType.IntType
                },
            ),
        ) {
            PlayerDetailScreen()
        }
    }
}