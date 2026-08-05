package com.whitebeach.presentation.players.detail

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
import com.whitebeach.presentation.players.model.PlayerUiModel

@Composable
fun PlayerDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: PlayerDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PlayerDetailScreenContent(
        uiState = uiState,
        onRetry = viewModel::loadPlayer,
        modifier = modifier,
    )
}

@Composable
private fun PlayerDetailScreenContent(
    uiState: PlayerDetailUiState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        PlayerDetailUiState.Loading -> {
            LoadingState(
                modifier = modifier,
            )
        }

        is PlayerDetailUiState.Success -> {
            PlayerDetailContent(
                player = uiState.player,
                modifier = modifier,
            )
        }

        is PlayerDetailUiState.Error -> {
            ErrorState(
                message = uiState.message,
                onRetry = onRetry,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun PlayerDetailContent(
    player: PlayerUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = player.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                PlayerDetailRow(
                    label = "Shirt number",
                    value = player.shirtNumber?.toString() ?: "-",
                )

                PlayerDetailRow(
                    label = "Position",
                    value = player.position.displayName,
                )

                PlayerDetailRow(
                    label = "Nationality",
                    value = player.nationality,
                )
            }
        }
    }
}

@Composable
private fun PlayerDetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Text(
            text = value,
            fontWeight = FontWeight.Medium,
        )
    }
}