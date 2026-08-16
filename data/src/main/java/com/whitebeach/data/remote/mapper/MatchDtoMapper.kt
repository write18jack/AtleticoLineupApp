package com.whitebeach.data.remote.mapper

import com.whitebeach.data.remote.dto.MatchDto
import com.whitebeach.domain.model.Match
import com.whitebeach.domain.model.MatchStatus

fun MatchDto.toDomain(): Match {
    return Match(
        id = id,
        competition = competition,
        date = date,
        time = time,
        status = status.toMatchStatus(),
        homeTeam = homeTeam,
        awayTeam = awayTeam,
        homeTeamImageUrl = homeTeamImageUrl,
        awayTeamImageUrl = awayTeamImageUrl,
        homeScore = homeScore,
        awayScore = awayScore,
    )
}

fun List<MatchDto>.toDomainModels(): List<Match> {
    return map(MatchDto::toDomain)
}

private fun String.toMatchStatus(): MatchStatus {
    return when (this) {
        "UPCOMING" -> MatchStatus.UPCOMING
        "FINISHED" -> MatchStatus.FINISHED
        else -> MatchStatus.UNKNOWN
    }
}