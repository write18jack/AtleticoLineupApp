package com.whitebeach.presentation.matches

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                timeText = match.timeText,
            )

            TeamRow(
                teamName = match.homeTeamName,
                score = match.homeScore,
            )

            TeamRow(
                teamName = match.awayTeamName,
                score = match.awayScore,
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
    score: Int?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = teamName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f),
        )

        Text(
            text = score?.toString() ?: "-",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchCardPreview() {
    AtleticoLineupAppTheme {
        MatchCard(
            match = MatchUiModel(
                id = 1,
                competitionName = "LaLiga",
                dateText = "Aug 17, 2026",
                timeText = "21:00",
                homeTeamName = "Atlético Madrid",
                awayTeamName = "Villarreal",
                homeScore = null,
                awayScore = null,
                status = MatchStatusUi.UPCOMING,
            ),
            onClick = {}
        )
    }
}