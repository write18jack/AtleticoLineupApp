package com.whitebeach.presentation.matches

import com.whitebeach.domain.model.Match
import com.whitebeach.domain.model.MatchStatus
import com.whitebeach.presentation.matches.model.MatchStatusUi
import com.whitebeach.presentation.matches.model.MatchUiModel

fun Match.toUiModel(): MatchUiModel {
    return MatchUiModel(
        id = id,
        competitionName = competition,
        dateText = date,
        timeText = time,
        homeTeamName = homeTeam,
        awayTeamName = awayTeam,
        status = status.toUiModel(),
        homeScore = homeScore,
        awayScore = awayScore,
    )
}

fun List<Match>.toUiModels(): List<MatchUiModel> {
    return map(Match::toUiModel)
}

private fun MatchStatus.toUiModel(): MatchStatusUi {
    return when (this) {
        MatchStatus.UPCOMING -> MatchStatusUi.UPCOMING
        MatchStatus.FINISHED -> MatchStatusUi.FINISHED
        MatchStatus.UNKNOWN -> MatchStatusUi.UNKNOWN
    }
}