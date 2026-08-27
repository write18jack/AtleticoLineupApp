package com.whitebeach.presentation.navigation

object PlayerDetailDestination {

    const val PLAYER_ID_ARGUMENT = "playerId"

    const val route = "player/{$PLAYER_ID_ARGUMENT}"

    fun createRoute(playerId: Int): String {
        return "player/$playerId"
    }
}