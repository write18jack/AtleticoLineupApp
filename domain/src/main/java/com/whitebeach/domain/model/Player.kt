package com.whitebeach.domain.model

data class Player(
    val id: Int,
    val name: String,
    val shirtNumber: Int?,
    val position: Position,
    val nationality: String,
)