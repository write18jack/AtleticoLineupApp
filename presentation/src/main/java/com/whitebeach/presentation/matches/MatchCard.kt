package com.whitebeach.presentation.matches

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.whitebeach.presentation.matches.model.MatchStatusUi
import com.whitebeach.presentation.matches.model.MatchUiModel
import com.whitebeach.presentation.theme.AtleticoLineupAppTheme

@Composable
fun MatchCard(
    match: MatchUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            MatchHeader(
                competitionName = match.competitionName,
                dateText = match.dateText,
                timeText = when (match.status) {
                    MatchStatusUi.UPCOMING -> match.timeText.ifBlank { "TBD" }

                    MatchStatusUi.FINISHED -> "FT"

                    MatchStatusUi.UNKNOWN -> ""
                },
            )

            TeamRow(
                teamName = match.homeTeamName,
                imageUrl = match.homeTeamImageUrl,
                score = match.homeScore,
                showScore = match.status == MatchStatusUi.FINISHED,
            )

            TeamRow(
                teamName = match.awayTeamName,
                imageUrl = match.awayTeamImageUrl,
                score = match.awayScore,
                showScore = match.status == MatchStatusUi.FINISHED,
            )
        }
    }
}

@Composable
private fun MatchHeader(
    competitionName: String,
    dateText: String,
    timeText: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = competitionName,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )

            Text(
                text = dateText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        Text(
            text = timeText,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun TeamRow(
    teamName: String,
    imageUrl: String?,
    score: Int?,
    showScore: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "$teamName logo",
            modifier = Modifier.size(36.dp),
            contentScale = ContentScale.Fit,
        )


        Text(
            text = teamName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f),
        )

        if (showScore) {
            Text(
                text = score?.toString() ?: "-",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UpcomingMatchCardPreview() {
    AtleticoLineupAppTheme {
        MatchCard(
            match = MatchUiModel(
                id = 2,
                competitionName = "LALIGA EA SPORTS",
                dateText = "Aug 19, 2026",
                timeText = "19:00",
                homeTeamName = "Atlético de Madrid",
                awayTeamName = "Málaga CF",
                homeTeamImageUrl = "",
                awayTeamImageUrl = "",
                homeScore = null,
                awayScore = null,
                status = MatchStatusUi.UPCOMING,
            ),
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FinishedMatchCardPreview() {
    AtleticoLineupAppTheme {
        MatchCard(
            match = MatchUiModel(
                id = 1,
                competitionName = "LALIGA EA SPORTS",
                dateText = "Feb 8, 2026",
                timeText = "17:30",
                homeTeamName = "Atlético de Madrid",
                awayTeamName = "Real Betis",
                homeTeamImageUrl = "",
                awayTeamImageUrl = "",
                homeScore = 0,
                awayScore = 1,
                status = MatchStatusUi.FINISHED,
            ),
            onClick = {},
        )
    }
}