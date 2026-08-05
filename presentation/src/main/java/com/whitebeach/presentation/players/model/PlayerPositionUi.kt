package com.whitebeach.presentation.players.model

enum class PlayerPositionUi(
    val displayName: String,
    val sectionTitle: String,
) {
    GOALKEEPER(
        displayName = "Goalkeeper",
        sectionTitle = "Goalkeepers",
    ),
    DEFENDER(
        displayName = "Defender",
        sectionTitle = "Defenders",
    ),
    MIDFIELDER(
        displayName = "Midfielder",
        sectionTitle = "Midfielders",
    ),
    FORWARD(
        displayName = "Forward",
        sectionTitle = "Forwards",
    ),
}