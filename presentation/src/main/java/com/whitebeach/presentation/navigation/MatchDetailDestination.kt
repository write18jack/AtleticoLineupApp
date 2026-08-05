package com.whitebeach.presentation.navigation

object MatchDetailDestination {

    const val MATCH_ID_ARGUMENT = "matchId"

    const val route = "match/{$MATCH_ID_ARGUMENT}"

    fun createRoute(matchId: Int): String {
        return "match/$matchId"
    }
}