package com.whitebeach.presentation.detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import com.whitebeach.presentation.players.detail.PlayerDetailContent
import com.whitebeach.presentation.players.model.PlayerPositionUi
import com.whitebeach.presentation.players.model.PlayerUiModel
import com.whitebeach.presentation.test.FailureScreenshotRule
import org.junit.Rule
import org.junit.Test

class PlayerDetailContentTest {

    @get:Rule(order = 0)
    val failureScreenshotRule = FailureScreenshotRule()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun playerDetail_displaysBasicPlayerInformation() {
        setContent(
            player = createPlayer(),
        )

        composeTestRule
            .onNodeWithText("Jan Oblak")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Goalkeeper")
            .performScrollTo()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Slovenia")
            .performScrollTo()
            .assertIsDisplayed()
    }

    @Test
    fun playerDetail_displaysBirthInformation() {
        setContent(
            player = createPlayer(
                birthDate = "1993-01-07",
                birthPlace = "Skofja Loka",
            ),
        )

        composeTestRule
            .onNodeWithText("1993-01-07")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Skofja Loka")
            .assertIsDisplayed()
    }

    @Test
    fun playerDetail_displaysGoalkeeperPosition() {
        setContent(
            player = createPlayer(
                position = PlayerPositionUi.GOALKEEPER,
            ),
        )

        composeTestRule
            .onNodeWithText(
                "Goalkeeper",
                ignoreCase = true,
            )
            .assertIsDisplayed()
    }

    @Test
    fun playerDetail_displaysForwardPosition() {
        setContent(
            player = createPlayer(
                name = "Julián Álvarez",
                shirtNumber = null,
                position = PlayerPositionUi.FORWARD,
                nationality = "Argentina",
            ),
        )

        composeTestRule
            .onNodeWithText("Julián Álvarez")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Forward")
            .performScrollTo()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Argentina")
            .performScrollTo()
            .assertIsDisplayed()
    }

    @Test
    fun playerDetail_doesNotDisplayBirthDate_whenBirthDateIsNull() {
        setContent(
            player = createPlayer(
                birthDate = null,
            ),
        )

        composeTestRule
            .onNodeWithText("1993-01-07")
            .assertDoesNotExist()
    }

    @Test
    fun playerDetail_doesNotDisplayBirthPlace_whenBirthPlaceIsNull() {
        setContent(
            player = createPlayer(
                birthPlace = null,
            ),
        )

        composeTestRule
            .onNodeWithText("Skofja Loka")
            .assertDoesNotExist()
    }

    @Test
    fun playerDetail_displaysPlayer_whenOptionalFieldsAreNull() {
        setContent(
            player = createPlayer(
                shirtNumber = null,
                imageUrl = null,
                birthDate = null,
                birthPlace = null,
            ),
        )

        composeTestRule
            .onNodeWithText("Jan Oblak")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Slovenia")
            .assertIsDisplayed()
    }

    private fun setContent(
        player: PlayerUiModel,
    ) {
        composeTestRule.setContent {
            MaterialTheme {
                PlayerDetailContent(
                    player = player,
                )
            }
        }
    }

    private fun createPlayer(
        id: Int = 1,
        name: String = "Jan Oblak",
        shirtNumber: Int? = null,
        position: PlayerPositionUi = PlayerPositionUi.GOALKEEPER,
        nationality: String = "Slovenia",
        imageUrl: String? = null,
        birthDate: String? = "1993-01-07",
        birthPlace: String? = "Skofja Loka",
    ): PlayerUiModel {
        return PlayerUiModel(
            id = id,
            name = name,
            shirtNumber = shirtNumber,
            position = position,
            nationality = nationality,
            imageUrl = imageUrl,
            birthDate = birthDate,
            birthPlace = birthPlace,
        )
    }
}