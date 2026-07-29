package com.whitebeach.presentation.players

data class PlayerUiModel(
    val id: Int,
    val shirtNumber: Int?,
    val name: String,
    val position: PlayerPositionUi,
    val nationality: String,
)

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