package com.whitebeach.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "players",
)
data class PlayerEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val shirtNumber: Int?,
    val position: String,
    val nationality: String,
    val imageUrl: String?,
    val birthDate: String?,
    val birthPlace: String?,
)