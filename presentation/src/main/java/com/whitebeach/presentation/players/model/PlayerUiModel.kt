package com.whitebeach.presentation.players.model

data class PlayerUiModel(
    val id: Int,
    val shirtNumber: Int? = null,
    val name: String,
    val position: PlayerPositionUi,
    val nationality: String,
    val imageUrl: String?,
    val birthDate: String?,
    val birthPlace: String?,
)
