package com.mbakgun.restaurants.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class FilterComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testFilterItem() {
        val selected = false
        val filterText = "Filter"

        composeTestRule.setContent {
            FilterItem(
                text = filterText,
                selectedFilter = selected,
                onSortingChanged = {
                    assert(it == selected.not())
                }
            )
        }

        composeTestRule
            .onNodeWithText(filterText)
            .assertIsDisplayed()
            .performClick()
    }
}
