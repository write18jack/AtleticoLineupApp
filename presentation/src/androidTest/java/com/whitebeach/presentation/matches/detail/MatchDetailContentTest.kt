package com.whitebeach.presentation.matches.detail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.whitebeach.presentation.matches.model.MatchStatusUi
import com.whitebeach.presentation.matches.model.MatchUiModel
import com.whitebeach.presentation.theme.AtleticoLineupAppTheme
import org.junit.Rule
import org.junit.Test

class MatchDetailContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun upcomingMatch_displaysMatchInformation() {
        val match = MatchUiModel(
            id = 1,
            competitionName = "LALIGA EA SPORTS",
            matchDayText = "Matchday 1",
            dateText = "Aug 20, 2026",
            timeText = "04:00",
            homeTeamName = "Atlético de Madrid",
            awayTeamName = "Málaga CF",
            homeTeamImageUrl = null,
            awayTeamImageUrl = null,
            venueName = "Riyadh Air Metropolitano",
            venueCity = "Madrid",
            homeScore = null,
            awayScore = null,
            status = MatchStatusUi.UPCOMING,
        )

        composeTestRule.setContent {
            AtleticoLineupAppTheme {
                MatchDetailContent(
                    match = match,
                )
            }
        }

        composeTestRule
            .onNodeWithText("LALIGA EA SPORTS")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Matchday 1")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Aug 20, 2026 · 04:00")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Atlético de Madrid")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Málaga CF")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("VS")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Riyadh Air Metropolitano")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Madrid")
            .assertIsDisplayed()
    }

    @Test
    fun finishedMatch_displaysScoreInsteadOfVs() {
        val match = MatchUiModel(
            id = 2,
            competitionName = "LALIGA EA SPORTS",
            matchDayText = "Matchday 5",
            dateText = "Sep 20, 2026",
            timeText = "21:00",
            homeTeamName = "Atlético de Madrid",
            awayTeamName = "Real Madrid",
            homeTeamImageUrl = null,
            awayTeamImageUrl = null,
            venueName = "Riyadh Air Metropolitano",
            venueCity = "Madrid",
            homeScore = 2,
            awayScore = 1,
            status = MatchStatusUi.FINISHED,
        )

        composeTestRule.setContent {
            AtleticoLineupAppTheme {
                MatchDetailContent(
                    match = match,
                )
            }
        }

        composeTestRule
            .onNodeWithText("2 - 1")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("VS")
            .assertDoesNotExist()
    }

    @Test
    fun unconfirmedKickoff_displaysTbd() {
        val match = MatchUiModel(
            id = 3,
            competitionName = "LALIGA EA SPORTS",
            matchDayText = null,
            dateText = "Sep 13, 2026",
            timeText = "TBD",
            homeTeamName = "Atlético de Madrid",
            awayTeamName = "Test Club",
            homeTeamImageUrl = null,
            awayTeamImageUrl = null,
            venueName = null,
            venueCity = null,
            homeScore = null,
            awayScore = null,
            status = MatchStatusUi.UPCOMING,
        )

        composeTestRule.setContent {
            AtleticoLineupAppTheme {
                MatchDetailContent(
                    match = match,
                )
            }
        }

        composeTestRule
            .onNodeWithText("Sep 13, 2026 · TBD")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Venue")
            .assertDoesNotExist()
    }
}