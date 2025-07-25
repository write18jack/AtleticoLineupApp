package com.whitebeach.presentation.formationSheet

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FormationSheetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `FormationSheet with empty list`() {
        composeTestRule.setContent {
            FormationSheet(
                list = emptyList(),
                onCLickTask = {}
            )
        }
    }

    @Test
    fun `FormationSheet with formationItemList`() {
        composeTestRule.setContent {
            FormationSheet(
                list = formationItemList,
                onCLickTask = {}
            )
        }
    }

    @Test
    fun `FormationSheet onCLickItem execution`() {
        composeTestRule.setContent {
            val formationState = rememberFormation()
            FormationSheet(
                list = formationItemList,
                onCLickTask = {
                    formationState.changeFormation(it)
                }
            )
        }
        composeTestRule.onNodeWithTag("5-3-2").performClick()

    }
}