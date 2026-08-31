package com.whitebeach.presentation.list

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.whitebeach.presentation.players.list.PlayersScreenContent
import com.whitebeach.presentation.players.list.PlayersUiState
import com.whitebeach.presentation.players.model.PlayerPositionUi
import com.whitebeach.presentation.players.model.PlayerUiModel
import com.whitebeach.presentation.test.FailureScreenshotRule
import org.junit.Rule
import org.junit.Test

class PlayersScreenContentTest {

    @get:Rule(order = 0)
    val failureScreenshotRule = FailureScreenshotRule()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun loading_displaysLoadingState() {
        setContent(
            uiState = PlayersUiState.Loading,
        )

        composeTestRule
            .onNodeWithTag("loadingIndicator")
            .assertIsDisplayed()
    }

    @Test
    fun error_displaysErrorMessage() {
        setContent(
            uiState = PlayersUiState.Error(
                message = "選手情報を取得できませんでした",
            ),
        )

        composeTestRule
            .onNodeWithText(
                "選手情報を取得できませんでした",
            )
            .assertIsDisplayed()
    }

    @Test
    fun success_displaysPlayers() {
        setContent(
            uiState = PlayersUiState.Success(
                players = previewPlayers,
                isRefreshing = false,
                refreshError = null,
            ),
        )

        composeTestRule
            .onNodeWithText("Jan Oblak")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Julián Álvarez")
            .assertIsDisplayed()
    }

    @Test
    fun goalkeeper_displaysPlayerInformation() {
        setContent(
            uiState = PlayersUiState.Success(
                players = listOf(
                    goalkeeper,
                ),
                isRefreshing = false,
                refreshError = null,
            ),
        )

        composeTestRule
            .onNodeWithText("Jan Oblak")
            .assertIsDisplayed()
    }

    @Test
    fun forward_displaysPlayerInformation() {
        setContent(
            uiState = PlayersUiState.Success(
                players = listOf(
                    forward,
                ),
                isRefreshing = false,
                refreshError = null,
            ),
        )

        composeTestRule
            .onNodeWithText("Julián Álvarez")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Argentina")
            .assertIsDisplayed()
    }

    @Test
    fun success_displaysMultiplePositions() {
        setContent(
            uiState = PlayersUiState.Success(
                players = previewPlayers,
                isRefreshing = false,
                refreshError = null,
            ),
        )

        composeTestRule
            .onNodeWithText("Jan Oblak")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Julián Álvarez")
            .assertIsDisplayed()
    }

    private fun setContent(
        uiState: PlayersUiState,
    ) {
        composeTestRule.setContent {
            MaterialTheme {
                PlayersScreenContent(
                    uiState = uiState,
                    onPlayerClick = {},
                    onRefresh = {},
                )
            }
        }
    }

    companion object {

        private val goalkeeper =
            PlayerUiModel(
                id = 1,
                name = "Jan Oblak",
                position = PlayerPositionUi.GOALKEEPER,
                nationality = "Slovenia",
                imageUrl = null,
                birthDate = "1993-01-07",
                birthPlace = "Skofja Loka",
            )

        private val forward =
            PlayerUiModel(
                id = 2,
                name = "Julián Álvarez",
                position = PlayerPositionUi.FORWARD,
                nationality = "Argentina",
                imageUrl = null,
                birthDate = null,
                birthPlace = null,
            )

        private val previewPlayers =
            listOf(
                goalkeeper,
                forward,
            )
    }
}