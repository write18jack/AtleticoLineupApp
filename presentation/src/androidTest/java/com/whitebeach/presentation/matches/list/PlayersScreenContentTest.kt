package com.whitebeach.presentation.players.list

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.whitebeach.presentation.players.model.PlayerPositionUi
import com.whitebeach.presentation.players.model.PlayerUiModel
import org.junit.Rule
import org.junit.Test

class PlayersScreenContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loading_displaysLoadingState() {
        setContent(
            uiState = PlayersUiState.Loading,
        )

        composeTestRule
            .onNodeWithText("Loading...")
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

        composeTestRule
            .onNodeWithText("13")
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
            .onNodeWithText("19")
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
                shirtNumber = 13,
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
                shirtNumber = 19,
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