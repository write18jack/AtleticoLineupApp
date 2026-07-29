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
import com.whitebeach.presentation.theme.AtleticoLineupAppTheme

@Composable
fun PlayersScreen(
    modifier: Modifier = Modifier,
) {
    PlayersContent(
        players = dummyPlayers,
        modifier = modifier,
    )
}

@Composable
private fun PlayersContent(
    players: List<PlayerUiModel>,
    modifier: Modifier = Modifier,
) {
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
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            ),
    ) {
        Text(
            text = "Squad",
            style = MaterialTheme.typography.headlineMedium,
        )

        Text(
            text = "$playerCount players",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
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

@Preview(showBackground = true)
@Composable
private fun PlayersScreenPreview() {
    AtleticoLineupAppTheme {
        PlayersContent(
            players = dummyPlayers,
        )
    }
}