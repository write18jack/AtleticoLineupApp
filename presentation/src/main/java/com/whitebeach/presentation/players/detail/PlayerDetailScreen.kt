package com.whitebeach.presentation.players.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
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
fun PlayerDetailContent(
    player: PlayerUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        AsyncImage(
            model = player.imageUrl,
            contentDescription = "${player.name} photo",
            modifier = Modifier
                .size(220.dp)
                .clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop,
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = player.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = player.position.displayName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )

            Text(
                text = player.nationality,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        PlayerInfoCard(
            player = player,
        )
    }
}

@Composable
private fun PlayerInfoCard(
    player: PlayerUiModel,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PlayerInfoRow(
                label = "Date of birth",
                value = player.birthDate ?: "-",
            )

            PlayerInfoRow(
                label = "Place of birth",
                value = player.birthPlace ?: "-",
            )
        }
    }
}

@Composable
private fun PlayerInfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
        )
    }
}