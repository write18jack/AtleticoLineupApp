package com.whitebeach.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Groups
import androidx.compose.ui.graphics.vector.ImageVector

enum class MainDestination(
    val label: String,
    val icon: ImageVector,
) {
    PLAYERS(
        label = "Players",
        icon = Icons.Default.Groups,
    ),
    MATCHES(
        label = "Matches",
        icon = Icons.Default.Event,
    ),
}