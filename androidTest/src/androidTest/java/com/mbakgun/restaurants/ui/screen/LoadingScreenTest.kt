package com.mbakgun.restaurants.ui.screen

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.mbakgun.androidtest.R
import org.junit.Rule
import org.junit.Test

class LoadingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoadingDisplayed() {
        lateinit var processTag: String

        composeTestRule.setContent {
            LoadingScreen()

            processTag = stringResource(id = R.string.test_progress_tag)
        }

        composeTestRule
            .onNodeWithTag(processTag)
            .assertIsDisplayed()
    }
}
