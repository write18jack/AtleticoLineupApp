package com.whitebeach.presentation.matches.model

data class MatchUiModel(
    val id: Int,
    val competitionName: String,
    val dateText: String,
    val timeText: String,
    val homeTeamName: String,
    val awayTeamName: String,
    val homeTeamImageUrl: String?,
    val awayTeamImageUrl: String?,
    val homeScore: Int?,
    val awayScore: Int?,
    val status: MatchStatusUi,
)