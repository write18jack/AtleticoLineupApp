package com.whitebeach.presentation.matches.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whitebeach.presentation.component.ErrorState
import com.whitebeach.presentation.component.LoadingState
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
private fun MatchDetailContent(
    match: MatchUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = match.competitionName,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = "${match.dateText} ${match.timeText}",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TeamDetailRow(
                    teamName = match.homeTeamName,
                    score = match.homeScore,
                )

                TeamDetailRow(
                    teamName = match.awayTeamName,
                    score = match.awayScore,
                )
            }
        }

        Text(
            text = "Status: ${match.status.sectionTitle}",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun TeamDetailRow(
    teamName: String,
    score: Int?,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = teamName,
            style = MaterialTheme.typography.titleMedium,
        )

        Text(
            text = score?.toString() ?: "-",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}