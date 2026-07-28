package com.whitebeach.presentation.playerSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class PlayerUiModel(
    val id: Int,
    val name: String,
    val position: String,
)

private val dummyPlayers = listOf(
    PlayerUiModel(
        id = 1,
        name = "Jan Oblak",
        position = "Goalkeeper",
    ),
    PlayerUiModel(
        id = 2,
        name = "José María Giménez",
        position = "Defender",
    ),
    PlayerUiModel(
        id = 3,
        name = "Koke",
        position = "Midfielder",
    ),
    PlayerUiModel(
        id = 4,
        name = "Antoine Griezmann",
        position = "Forward",
    ),
)

@Composable
fun PlayerSheet(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        dummyPlayers.forEach { player ->
            Text(
                text = "${player.name} / ${player.position}",
            )
        }
    }
}