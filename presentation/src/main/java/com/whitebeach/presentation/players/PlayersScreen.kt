package com.whitebeach.presentation.players

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whitebeach.presentation.component.EmptyState
import com.whitebeach.presentation.component.ErrorState
import com.whitebeach.presentation.component.LoadingState
import com.whitebeach.presentation.theme.AtleticoLineupAppTheme

@Composable
fun PlayersScreen(
    modifier: Modifier = Modifier,
) {
    PlayersScreen(
        uiState = PlayersUiState.Success(
            players = dummyPlayers,
        ),
        onRetry = {},
        modifier = modifier,
    )
}

@Composable
private fun PlayersScreen(
    uiState: PlayersUiState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        PlayersUiState.Loading -> {
            LoadingState(
                modifier = modifier,
            )
        }

        is PlayersUiState.Success -> {
            PlayersContent(
                players = uiState.players,
                modifier = modifier,
            )
        }

        is PlayersUiState.Error -> {
            ErrorState(
                message = uiState.message,
                onRetry = onRetry,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun PlayersContent(
    players: List<PlayerUiModel>,
    modifier: Modifier = Modifier,
) {
    if (players.isEmpty()) {
        EmptyState(
            title = "No players found",
            description = "Player information is not available.",
            modifier = modifier,
        )

        return
    }

    val playersByPosition = remember(players) {
        players.groupBy { player ->
            player.position
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        PlayersHeader(
            playerCount = players.size,
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                top = 8.dp,
                end = 16.dp,
                bottom = 24.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            PlayerPositionUi.entries.forEach { position ->
                playerPositionSection(
                    position = position,
                    players = playersByPosition[position].orEmpty(),
                )
            }
        }
    }
}

private fun LazyListScope.playerPositionSection(
    position: PlayerPositionUi,
    players: List<PlayerUiModel>,
) {
    if (players.isEmpty()) {
        return
    }

    item(
        key = "header_${position.name}",
    ) {
        PositionSectionHeader(
            position = position,
            playerCount = players.size,
        )
    }

    items(
        items = players,
        key = { player ->
            player.id
        },
    ) { player ->
        PlayerCard(
            player = player,
        )
    }
}

@Composable
private fun PlayersHeader(
    playerCount: Int,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "$playerCount players",
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            ),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
private fun PositionSectionHeader(
    position: PlayerPositionUi,
    playerCount: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 12.dp,
                bottom = 4.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "${position.sectionTitle} ($playerCount)",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )

        HorizontalDivider()
    }
}

@Preview(
    name = "Loading players",
    showBackground = true,
)
@Composable
private fun LoadingPlayersPreview() {
    AtleticoLineupAppTheme {
        PlayersScreen(
            uiState = PlayersUiState.Loading,
            onRetry = {},
        )
    }
}

@Preview(
    name = "Success players",
    showBackground = true,
)
@Composable
private fun SuccessPlayersPreview() {
    AtleticoLineupAppTheme {
        PlayersScreen(
            uiState = PlayersUiState.Success(
                players = dummyPlayers,
            ),
            onRetry = {},
        )
    }
}

@Preview(
    name = "Empty players",
    showBackground = true,
)
@Composable
private fun EmptyPlayersPreview() {
    AtleticoLineupAppTheme {
        PlayersScreen(
            uiState = PlayersUiState.Success(
                players = emptyList(),
            ),
            onRetry = {},
        )
    }
}

@Preview(
    name = "Error players",
    showBackground = true,
)
@Composable
private fun ErrorPlayersPreview() {
    AtleticoLineupAppTheme {
        PlayersScreen(
            uiState = PlayersUiState.Error(
                message = "Failed to load players.",
            ),
            onRetry = {},
        )
    }
}