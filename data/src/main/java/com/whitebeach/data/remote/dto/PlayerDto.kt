package com.whitebeach.data.remote.dto

data class PlayerDto(
    val id: Int,
    val name: String,
    val shirtNumber: Int?,
    val position: String,
    val nationality: String,
    val imageUrl: String?,
    val birthDate: String?,
    val birthPlace: String?,
)