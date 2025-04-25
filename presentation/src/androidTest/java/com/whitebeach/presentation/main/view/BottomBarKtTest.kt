package com.whitebeach.presentation.main.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BottomBarKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `BottomBar Player Sheet Open Check`() {
        composeTestRule.setContent {
            BottomBar(
                openPlayerSheet = { /*TODO*/ },
                openFormationSheet = { /*TODO*/ }
            )
        }
        composeTestRule.onNodeWithContentDescription("player_list").performClick()
    }

    @Test
    fun `BottomBar Formation Sheet Open Check`() {
        composeTestRule.setContent {
            BottomBar(
                openPlayerSheet = { },
                openFormationSheet = { }
            )
        }
        composeTestRule.onNodeWithContentDescription("formation_list").performClick()
    }
}