package com.whitebeach.data.local.mapper

import com.whitebeach.data.local.entity.MatchEntity
import com.whitebeach.domain.model.Match
import com.whitebeach.domain.model.MatchStatus

fun MatchEntity.toDomain(): Match {
    return Match(
        id = id,
        competition = competition,
        homeTeam = homeTeam,
        awayTeam = awayTeam,
        date = date,
        time = time,
        status = status.toDomainStatus(),
        homeScore = homeScore,
        awayScore = awayScore,
    )
}

fun Match.toEntity(): MatchEntity {
    return MatchEntity(
        id = id,
        competition = competition,
        homeTeam = homeTeam,
        awayTeam = awayTeam,
        date = date,
        time = time,
        status = status.name,
        homeScore = homeScore,
        awayScore = awayScore,
    )
}

fun List<MatchEntity>.toDomainModels(): List<Match> {
    return map(MatchEntity::toDomain)
}

fun List<Match>.toEntities(): List<MatchEntity> {
    return map(Match::toEntity)
}

private fun String.toDomainStatus(): MatchStatus {
    return runCatching {
        MatchStatus.valueOf(this)
    }.getOrDefault(MatchStatus.UNKNOWN)
}