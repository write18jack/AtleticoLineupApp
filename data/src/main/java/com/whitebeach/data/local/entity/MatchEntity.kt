package com.whitebeach.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class MatchEntity(
    @PrimaryKey
    val id: Int,
    val competition: String,
    val matchDay: String?,
    val homeTeam: String,
    val awayTeam: String,
    val homeTeamImageUrl: String?,
    val awayTeamImageUrl: String?,
    val scheduledDate: String,
    val kickoffAt: String?,
    val venueName: String?,
    val venueCity: String?,
    val status: String,
    val homeScore: Int?,
    val awayScore: Int?,
)