package com.whitebeach.presentation.list

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.whitebeach.presentation.matches.list.MatchesScreenContent
import com.whitebeach.presentation.matches.list.MatchesUiState
import com.whitebeach.presentation.matches.model.MatchStatusUi
import com.whitebeach.presentation.matches.model.MatchUiModel
import com.whitebeach.presentation.test.FailureScreenshotRule
import org.junit.Rule
import org.junit.Test

class MatchesScreenContentTest {

    @get:Rule(order = 0)
    val failureScreenshotRule = FailureScreenshotRule()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun loading_displaysLoadingState() {
        setContent(
            uiState = MatchesUiState.Loading,
        )

        composeTestRule
            .onNodeWithTag("loadingIndicator")
            .assertIsDisplayed()
    }

    @Test
    fun error_displaysErrorMessage() {
        setContent(
            uiState = MatchesUiState.Error(
                message = "試合情報を取得できませんでした",
            ),
        )

        composeTestRule
            .onNodeWithText(
                "試合情報を取得できませんでした",
            )
            .assertIsDisplayed()
    }

    @Test
    fun success_displaysMatches() {
        setContent(
            uiState = MatchesUiState.Success(
                matches = previewMatches,
                isRefreshing = false,
                refreshError = null,
            ),
        )

        composeTestRule
            .onNodeWithText("Barcelona")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Sevilla")
            .assertIsDisplayed()
    }

    @Test
    fun upcomingMatch_displaysTimeAndDoesNotDisplayScore() {
        setContent(
            uiState = MatchesUiState.Success(
                matches = listOf(
                    upcomingMatch,
                ),
                isRefreshing = false,
                refreshError = null,
            ),
        )

        composeTestRule
            .onNodeWithText("20:00")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("2 - 1")
            .assertDoesNotExist()
    }

    @Test
    fun finishedMatch_displaysScore() {
        setContent(
            uiState = MatchesUiState.Success(
                matches = listOf(
                    finishedMatch,
                ),
                isRefreshing = false,
                refreshError = null,
            ),
        )

        composeTestRule
            .onNodeWithText("2")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("1")
            .assertIsDisplayed()
    }

    @Test
    fun success_displaysCompetitionAndDate() {
        setContent(
            uiState = MatchesUiState.Success(
                matches = listOf(
                    upcomingMatch,
                ),
                isRefreshing = false,
                refreshError = null,
            ),
        )

        composeTestRule
            .onNodeWithText("LaLiga")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("2026-08-15")
            .assertIsDisplayed()
    }

    private fun setContent(
        uiState: MatchesUiState,
    ) {
        composeTestRule.setContent {
            MaterialTheme {
                MatchesScreenContent(
                    uiState = uiState,
                    onMatchClick = {},
                    onRefresh = {},
                )
            }
        }
    }

    companion object {

        private val upcomingMatch =
            MatchUiModel(
                id = 1,
                competitionName = "LaLiga",
                matchDayText = "Matchday 1",
                dateText = "2026-08-15",
                timeText = "20:00",
                homeTeamName = "Atlético Madrid",
                awayTeamName = "Barcelona",
                homeTeamImageUrl = null,
                awayTeamImageUrl = null,
                venueName = "Riyadh Air Metropolitano",
                venueCity = "Madrid",
                homeScore = null,
                awayScore = null,
                status = MatchStatusUi.UPCOMING,
            )

        private val finishedMatch =
            MatchUiModel(
                id = 2,
                competitionName = "LaLiga",
                matchDayText = "Matchday 2",
                dateText = "2026-08-08",
                timeText = "20:00",
                homeTeamName = "Atlético Madrid",
                awayTeamName = "Sevilla",
                homeTeamImageUrl = null,
                awayTeamImageUrl = null,
                venueName = "Riyadh Air Metropolitano",
                venueCity = "Madrid",
                homeScore = 2,
                awayScore = 1,
                status = MatchStatusUi.FINISHED,
            )

        private val previewMatches =
            listOf(
                upcomingMatch,
                finishedMatch,
            )
    }
}