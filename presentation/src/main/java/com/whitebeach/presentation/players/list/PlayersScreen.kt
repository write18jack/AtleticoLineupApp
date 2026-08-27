package com.whitebeach.presentation.players.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whitebeach.presentation.component.EmptyState
import com.whitebeach.presentation.component.ErrorState
import com.whitebeach.presentation.component.LoadingState
import com.whitebeach.presentation.players.model.PlayerPositionUi
import com.whitebeach.presentation.players.model.PlayerUiModel
import com.whitebeach.presentation.players.model.previewPlayers
import com.whitebeach.presentation.theme.AtleticoLineupAppTheme

@Composable
fun PlayersScreen(
    onPlayerClick: (Int) -> Unit,
    snackbarHostState: SnackbarHostState,
    viewModel: PlayersViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val refreshError = (uiState as? PlayersUiState.Success)?.refreshError

    LaunchedEffect(refreshError) {
        refreshError?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
            )
        }
    }

    PlayersScreenContent(
        uiState = uiState,
        onPlayerClick = onPlayerClick,
        onRefresh = viewModel::refreshPlayers,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlayersScreenContent(
    uiState: PlayersUiState,
    onPlayerClick: (Int) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        PlayersUiState.Loading -> {
            LoadingState(
                modifier = modifier,
            )
        }

        is PlayersUiState.Error -> {
            ErrorState(
                message = uiState.message,
                onRetry = onRefresh,
                modifier = modifier,
            )
        }

        is PlayersUiState.Success -> {
            PullToRefreshBox(
                isRefreshing = uiState.isRefreshing,
                onRefresh = onRefresh,
                modifier = modifier.fillMaxSize(),
            ) {
                PlayersContent(
                    players = uiState.players,
                    onPlayerClick = onPlayerClick,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
private fun PlayersContent(
    players: List<PlayerUiModel>,
    onPlayerClick: (Int) -> Unit,
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
        players.groupBy(PlayerUiModel::position)
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
                    onPlayerClick = onPlayerClick,
                )
            }
        }
    }
}

private fun LazyListScope.playerPositionSection(
    position: PlayerPositionUi,
    players: List<PlayerUiModel>,
    onPlayerClick: (Int) -> Unit,
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
        key = PlayerUiModel::id,
    ) { player ->
        PlayerCard(
            player = player,
            onClick = {
                onPlayerClick(player.id)
            },
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
        PlayersScreenContent(
            uiState = PlayersUiState.Loading,
            onPlayerClick = {},
            onRefresh = {},
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
        PlayersScreenContent(
            uiState = PlayersUiState.Success(
                players = previewPlayers,
            ),
            onPlayerClick = {},
            onRefresh = {},
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
        PlayersScreenContent(
            uiState = PlayersUiState.Success(
                players = emptyList(),
            ),
            onPlayerClick = {},
            onRefresh = {},
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
        PlayersScreenContent(
            uiState = PlayersUiState.Error(
                message = "Failed to load players.",
            ),
            onPlayerClick = {},
            onRefresh = {},
        )
    }
}