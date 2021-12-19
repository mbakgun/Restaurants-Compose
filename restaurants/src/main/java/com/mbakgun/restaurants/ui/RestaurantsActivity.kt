package com.mbakgun.restaurants.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import com.google.accompanist.insets.ProvideWindowInsets
import com.mbakgun.core.compose.QuickSandTypography
import com.mbakgun.restaurants.ui.screen.RestaurantsActivityScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDecorFitsSystemWindows(window, false)
        setContent {
            MaterialTheme(typography = QuickSandTypography) {
                ProvideWindowInsets {
                    RestaurantsActivityScreen()
                }
            }
        }
    }
}
