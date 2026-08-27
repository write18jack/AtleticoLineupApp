package com.whitebeach.presentation.matches.list

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
import com.whitebeach.presentation.matches.model.MatchStatusUi
import com.whitebeach.presentation.matches.model.MatchUiModel
import com.whitebeach.presentation.matches.model.previewMatches
import com.whitebeach.presentation.theme.AtleticoLineupAppTheme

/*
* Entry point
*/
@Composable
fun MatchesScreen(
    onMatchClick: (Int) -> Unit,
    snackbarHostState: SnackbarHostState,
    viewModel: MatchesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val refreshError = (uiState as? MatchesUiState.Success)?.refreshError

    LaunchedEffect(refreshError) {
        refreshError?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
            )
        }
    }

    MatchesScreenContent(
        uiState = uiState,
        onMatchClick = onMatchClick,
        onRefresh = viewModel::refreshMatches,
    )
}

/*
 * UiStateを描画するComposable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MatchesScreenContent(
    uiState: MatchesUiState,
    onMatchClick: (Int) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        MatchesUiState.Loading -> {
            LoadingState(
                modifier = modifier,
            )
        }

        is MatchesUiState.Error -> {
            ErrorState(
                message = uiState.message,
                onRetry = onRefresh,
                modifier = modifier,
            )
        }

        is MatchesUiState.Success -> {
            PullToRefreshBox(
                isRefreshing = uiState.isRefreshing,
                onRefresh = onRefresh,
                modifier = modifier.fillMaxSize(),
            ) {
                MatchesContent(
                    matches = uiState.matches,
                    onMatchClick = onMatchClick,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
private fun MatchesContent(
    matches: List<MatchUiModel>,
    onMatchClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (matches.isEmpty()) {
        EmptyState(
            title = "No matches found",
            description = "Match information is not available.",
            modifier = modifier,
        )
        return
    }

    val matchesByStatus = remember(matches) {
        matches.groupBy(MatchUiModel::status)
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
                    onMatchClick = onMatchClick,
                )
            }
        }
    }
}

private fun LazyListScope.matchStatusSection(
    status: MatchStatusUi,
    matches: List<MatchUiModel>,
    onMatchClick: (Int) -> Unit,
) {
    if (matches.isEmpty()) return

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
        key = MatchUiModel::id,
    ) { match ->
        MatchCard(
            match = match,
            onClick = {
                onMatchClick(match.id)
            },
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

@Preview(
    name = "Loading matches",
    showBackground = true,
)
@Composable
private fun LoadingMatchesPreview() {
    AtleticoLineupAppTheme {
        MatchesScreenContent(
            uiState = MatchesUiState.Loading,
            onMatchClick = {},
            onRefresh = {},
        )
    }
}

@Preview(
    name = "Success matches",
    showBackground = true,
)
@Composable
private fun SuccessMatchesPreview() {
    AtleticoLineupAppTheme {
        MatchesScreenContent(
            uiState = MatchesUiState.Success(
                matches = previewMatches,
            ),
            onMatchClick = {},
            onRefresh = {},
        )
    }
}

@Preview(
    name = "Empty matches",
    showBackground = true,
)
@Composable
private fun EmptyMatchesPreview() {
    AtleticoLineupAppTheme {
        MatchesScreenContent(
            uiState = MatchesUiState.Success(
                matches = emptyList(),
            ),
            onMatchClick = {},
            onRefresh = {},
        )
    }
}

@Preview(
    name = "Error matches",
    showBackground = true,
)
@Composable
private fun ErrorMatchesPreview() {
    AtleticoLineupAppTheme {
        MatchesScreenContent(
            uiState = MatchesUiState.Error(
                message = "Failed to load matches.",
            ),
            onMatchClick = {},
            onRefresh = {},
        )
    }
}