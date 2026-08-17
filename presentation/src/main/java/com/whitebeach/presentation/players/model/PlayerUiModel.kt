package com.whitebeach.presentation.players.model

data class PlayerUiModel(
    val id: Int,
    val shirtNumber: Int?,
    val name: String,
    val position: PlayerPositionUi,
    val nationality: String,
    val imageUrl: String?
)
