package com.mbakgun.restaurants.ui.screen

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.mbakgun.androidtest.R
import org.junit.Rule
import org.junit.Test

class ErrorScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testErrorScreenScenario() {
        val message = "Timeout"

        composeTestRule.setContent {
            ErrorScreen(
                errorMessage = message,
            )
        }

        composeTestRule
            .onNodeWithText(message)
            .assertIsDisplayed()
    }

    @Test
    fun testErrorScreenScenarioNullText() {
        lateinit var generic: String
        val message = null

        composeTestRule.setContent {
            ErrorScreen(
                errorMessage = message,
            )

            generic = stringResource(id = R.string.genericErrorMessage)
        }

        composeTestRule
            .onNodeWithText(generic)
            .assertIsDisplayed()
    }
}
