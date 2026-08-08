package com.whitebeach.data.remote.dto

data class MatchDto(
    val id: Int,
    val competition: String,
    val homeTeam: String,
    val awayTeam: String,
    val date: String,
    val time: String,
    val status: String,
    val homeScore: Int?,
    val awayScore: Int?,
)