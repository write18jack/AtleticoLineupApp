package com.whitebeach.presentation.matches.detail

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.whitebeach.presentation.component.ErrorState
import com.whitebeach.presentation.component.LoadingState
import com.whitebeach.presentation.matches.model.MatchStatusUi
import com.whitebeach.presentation.matches.model.MatchUiModel

@Composable
fun MatchDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: MatchDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MatchDetailScreenContent(
        uiState = uiState,
        onRetry = viewModel::loadMatch,
        modifier = modifier,
    )
}

@Composable
private fun MatchDetailScreenContent(
    uiState: MatchDetailUiState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        MatchDetailUiState.Loading -> {
            LoadingState(modifier = modifier)
        }

        is MatchDetailUiState.Success -> {
            MatchDetailContent(
                match = uiState.match,
                modifier = modifier,
            )
        }

        is MatchDetailUiState.Error -> {
            ErrorState(
                message = uiState.message,
                onRetry = onRetry,
                modifier = modifier,
            )
        }
    }
}

@Composable
fun MatchDetailContent(
    match: MatchUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Log.d("MatchCompetitionSection", "matchDayText=${match.matchDayText}")
        MatchCompetitionSection(
            competitionName = match.competitionName,
            matchDayText = match.matchDayText,
        )

        MatchDateTimeSection(
            dateText = match.dateText,
            timeText = match.timeText,
        )

        MatchTeamsSection(
            match = match,
        )

        MatchVenueSection(
            venueName = match.venueName,
            venueCity = match.venueCity,
        )
    }
}

@Composable
private fun MatchCompetitionSection(
    competitionName: String,
    matchDayText: String?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = competitionName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )

        matchDayText?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun MatchDateTimeSection(
    dateText: String,
    timeText: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "$dateText · $timeText",
        modifier = modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
private fun MatchTeamsSection(
    match: MatchUiModel,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MatchDetailTeam(
                teamName = match.homeTeamName,
                imageUrl = match.homeTeamImageUrl,
                modifier = Modifier.weight(1f),
            )

            MatchScore(
                match = match,
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            MatchDetailTeam(
                teamName = match.awayTeamName,
                imageUrl = match.awayTeamImageUrl,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun MatchDetailTeam(
    teamName: String,
    imageUrl: String?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "$teamName emblem",
            modifier = Modifier.size(72.dp),
            contentScale = ContentScale.Fit,
        )

        Text(
            text = teamName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun MatchScore(
    match: MatchUiModel,
    modifier: Modifier = Modifier,
) {
    val scoreText = when (match.status) {
        MatchStatusUi.FINISHED -> {
            "${match.homeScore ?: "-"} - ${match.awayScore ?: "-"}"
        }

        MatchStatusUi.UPCOMING -> {
            "VS"
        }

        MatchStatusUi.UNKNOWN -> {
            "-"
        }
    }

    Text(
        text = scoreText,
        modifier = modifier,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
private fun MatchVenueSection(
    venueName: String?,
    venueCity: String?,
    modifier: Modifier = Modifier,
) {
    if (venueName == null && venueCity == null) {
        return
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "Venue",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )

            venueName?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                )
            }

            venueCity?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}