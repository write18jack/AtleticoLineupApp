package com.whitebeach.presentation.matches

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
fun MatchesScreen(
    modifier: Modifier = Modifier,
) {
    MatchesContent(
        matches = dummyMatches,
        modifier = modifier,
    )
}

@Composable
private fun MatchesContent(
    matches: List<MatchUiModel>,
    modifier: Modifier = Modifier,
) {
    val matchesByStatus = remember(matches) {
        matches.groupBy { match ->
            match.status
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        MatchesHeader(
            matchCount = matches.size,
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
            MatchStatusUi.entries.forEach { status ->
                matchStatusSection(
                    status = status,
                    matches = matchesByStatus[status].orEmpty(),
                )
            }
        }
    }
}

private fun LazyListScope.matchStatusSection(
    status: MatchStatusUi,
    matches: List<MatchUiModel>,
) {
    if (matches.isEmpty()) {
        return
    }

    item(
        key = "header_${status.name}",
    ) {
        MatchSectionHeader(
            title = status.sectionTitle,
            matchCount = matches.size,
        )
    }

    items(
        items = matches,
        key = { match ->
            match.id
        },
    ) { match ->
        MatchCard(
            match = match,
        )
    }
}

@Composable
private fun MatchesHeader(
    matchCount: Int,
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
            text = "Matches",
            style = MaterialTheme.typography.headlineMedium,
        )

        Text(
            text = "$matchCount matches",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun MatchSectionHeader(
    title: String,
    matchCount: Int,
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
            text = "$title ($matchCount)",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )

        HorizontalDivider()
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchesScreenPreview() {
    AtleticoLineupAppTheme {
        MatchesContent(
            matches = dummyMatches,
        )
    }
}