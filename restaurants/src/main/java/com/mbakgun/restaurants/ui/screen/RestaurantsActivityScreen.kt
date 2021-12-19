package com.mbakgun.restaurants.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.mbakgun.core.domain.model.Result.Error
import com.mbakgun.core.domain.model.Result.Loading
import com.mbakgun.core.domain.model.Result.Success
import com.mbakgun.restaurants.ui.RestaurantsViewModel

@Composable
fun RestaurantsActivityScreen() {
    val viewModel = hiltViewModel<RestaurantsViewModel>()
    when (val result = viewModel.restaurantsFlow.collectAsState().value) {
        is Loading -> LoadingScreen()
        is Error -> ErrorScreen(result.exception.localizedMessage)
        is Success -> RestaurantListScreen(result.data.restaurants)
    }
}
