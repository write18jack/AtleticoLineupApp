package com.whitebeach.data.datasource.dummy

import com.whitebeach.domain.model.Match
import com.whitebeach.domain.model.MatchStatus

internal val dummyMatches = listOf(
    Match(
        id = 1,
        competition = "LaLiga",
        date = "Aug 17, 2026",
        time = "21:00",
        homeTeam = "Atlético Madrid",
        awayTeam = "Villarreal",
        status = MatchStatus.UPCOMING,
        homeScore = null,
        awayScore = null,
    ),
    Match(
        id = 2,
        competition = "LaLiga",
        date = "Aug 24, 2026",
        time = "19:30",
        homeTeam = "Real Betis",
        awayTeam = "Atlético Madrid",
        status = MatchStatus.UPCOMING,
        homeScore = null,
        awayScore = null
    ),
    Match(
        id = 3,
        competition = "Club Friendly",
        date = "Jul 26, 2026",
        time = "Finished",
        homeTeam = "Atlético Madrid",
        awayTeam = "Inter",
        status = MatchStatus.FINISHED,
        homeScore = 2,
        awayScore = 1
    ),
    Match(
        id = 4,
        competition = "Club Friendly",
        date = "Jul 20, 2026",
        time = "Finished",
        homeTeam = "Arsenal",
        awayTeam = "Atlético Madrid",
        status = MatchStatus.FINISHED,
        homeScore = 1,
        awayScore = 1
    ),
)