package com.whitebeach.domain.model

data class Match(
    val id: Int,
    val competition: String,
    val homeTeam: String,
    val awayTeam: String,
    val date: String,
    val time: String,
    val status: MatchStatus,
    val homeScore: Int?,
    val awayScore: Int?,
)