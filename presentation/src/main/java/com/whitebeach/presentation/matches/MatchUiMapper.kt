package com.whitebeach.presentation.matches

import com.whitebeach.domain.model.Match
import com.whitebeach.domain.model.MatchStatus
import com.whitebeach.presentation.matches.model.MatchStatusUi
import com.whitebeach.presentation.matches.model.MatchUiModel
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Match.toUiModel(): MatchUiModel {
    val localDateTime = kickoffAt?.toLocalDateTimeText()

    return MatchUiModel(
        id = id,
        competitionName = competition,
        matchDayText = matchDay,
        dateText = localDateTime?.first ?: scheduledDate.toDateOnlyText(),
        timeText = localDateTime?.second ?: "TBD",

        homeTeamName = homeTeam,
        awayTeamName = awayTeam,

        homeTeamImageUrl = homeTeamImageUrl,
        awayTeamImageUrl = awayTeamImageUrl,

        venueName = venueName,
        venueCity = venueCity,

        homeScore = homeScore,
        awayScore = awayScore,

        status = status.toUiModel(),
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

private fun String.toLocalDateTimeText(): Pair<String, String> {
    val offsetDateTime = OffsetDateTime.parse(this)

    val localDateTime = offsetDateTime
        .atZoneSameInstant(ZoneId.systemDefault())

    val dateText = localDateTime.format(
        DateTimeFormatter.ofPattern("MMM d, yyyy"),
    )

    val timeText = localDateTime.format(
        DateTimeFormatter.ofPattern("HH:mm"),
    )

    return dateText to timeText
}

fun String.toDateOnlyText(): String {
    return runCatching {
        val date = LocalDate.parse(
            substringBefore("T"),
        )

        date.format(
            DateTimeFormatter.ofPattern(
                "MMM d, yyyy",
                Locale.ENGLISH,
            ),
        )
    }.getOrElse {
        this
    }
}