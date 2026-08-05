package com.whitebeach.presentation.matches.model

val previewMatches = listOf(
    MatchUiModel(
        id = 1,
        competitionName = "LaLiga",
        dateText = "2026-08-15",
        timeText = "20:00",
        homeTeamName = "Atlético Madrid",
        awayTeamName = "Barcelona",
        homeScore = null,
        awayScore = null,
        status = MatchStatusUi.UPCOMING,
    ),
    MatchUiModel(
        id = 2,
        competitionName = "LaLiga",
        dateText = "2026-08-08",
        timeText = "Finished",
        homeTeamName = "Atlético Madrid",
        awayTeamName = "Sevilla",
        homeScore = 2,
        awayScore = 1,
        status = MatchStatusUi.FINISHED,
    )
)