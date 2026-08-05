package com.whitebeach.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Groups
import androidx.compose.ui.graphics.vector.ImageVector

enum class MainDestination(
    val route: String,
    val title: String,
    val label: String,
    val icon: ImageVector,
) {
    PLAYERS(
        route = "players",
        title = "Atlético Squad",
        label = "Players",
        icon = Icons.Default.Groups,
    ),
    MATCHES(
        route = "matches",
        title = "Atlético Matches",
        label = "Matches",
        icon = Icons.Default.Event,
    ),
    ;

    companion object {
        fun fromRoute(route: String?): MainDestination {
            return entries.firstOrNull { destination ->
                destination.route == route
            } ?: PLAYERS
        }
    }
}