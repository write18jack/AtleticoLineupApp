package com.whitebeach.data.remote.mapper

import com.whitebeach.data.remote.dto.MatchDto
import com.whitebeach.domain.model.Match
import com.whitebeach.domain.model.MatchStatus

fun MatchDto.toDomain(): Match {
    return Match(
        id = id,
        competition = competition,
        matchDay = matchDay,
        scheduledDate = scheduledDate,
        kickoffAt = kickoffAt,
        homeTeam = homeTeam,
        awayTeam = awayTeam,
        homeTeamImageUrl = homeTeamImageUrl,
        awayTeamImageUrl = awayTeamImageUrl,
        venueName = venueName,
        venueCity = venueCity,
        status = status.toMatchStatus(),
        homeScore = homeScore,
        awayScore = awayScore,
    )
}

fun List<MatchDto>.toDomainMatches(): List<Match> {
    return map(MatchDto::toDomain)
}

private fun String.toMatchStatus(): MatchStatus {
    return when (this) {
        "UPCOMING" -> MatchStatus.UPCOMING
        "FINISHED" -> MatchStatus.FINISHED
        else -> MatchStatus.UNKNOWN
    }
}