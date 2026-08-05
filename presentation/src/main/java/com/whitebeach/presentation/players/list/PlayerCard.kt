package com.whitebeach.presentation.players.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whitebeach.presentation.players.model.PlayerUiModel
import com.whitebeach.presentation.players.model.previewPlayers
import com.whitebeach.presentation.theme.AtleticoLineupAppTheme

@Composable
fun PlayerCard(
    player: PlayerUiModel,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ShirtNumber(
                shirtNumber = player.shirtNumber,
            )

            Spacer(
                modifier = Modifier.width(16.dp),
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = player.position.displayName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )

                Text(
                    text = player.nationality,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun ShirtNumber(
    shirtNumber: Int?,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.size(64.dp),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = shirtNumber?.toString() ?: "-",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayerCardPreview() {
    AtleticoLineupAppTheme {
        PlayerCard(
            player = previewPlayers[0],
            onClick = {},
        )
    }
}