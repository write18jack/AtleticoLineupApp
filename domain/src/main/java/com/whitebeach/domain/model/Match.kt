package com.whitebeach.domain.model

data class Match(
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
    val status: MatchStatus,
    val homeScore: Int?,
    val awayScore: Int?,
)