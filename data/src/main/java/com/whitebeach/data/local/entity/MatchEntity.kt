package com.whitebeach.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "matches",
)
data class MatchEntity(
    @PrimaryKey
    val id: Int,
    val competition: String,
    val date: String,
    val time: String,
    val status: String,
    val homeTeam: String,
    val awayTeam: String,
    val homeTeamImageUrl: String?,
    val awayTeamImageUrl: String?,
    val homeScore: Int?,
    val awayScore: Int?,
)