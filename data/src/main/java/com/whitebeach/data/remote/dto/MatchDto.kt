package com.whitebeach.data.remote.dto

data class MatchDto(
    val id: Int,
    val competition: String,
    val matchDay: String?,
    val scheduledDate: String,
    val kickoffAt: String?,
    val homeTeam: String,
    val awayTeam: String,
    val homeTeamImageUrl: String?,
    val awayTeamImageUrl: String?,
    val venueName: String?,
    val venueCity: String?,
    val status: String,
    val homeScore: Int?,
    val awayScore: Int?,
)