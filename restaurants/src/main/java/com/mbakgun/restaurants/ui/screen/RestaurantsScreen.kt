package com.mbakgun.restaurants.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.mbakgun.core.domain.model.Result
import com.mbakgun.core.domain.model.Result.Error
import com.mbakgun.core.domain.model.Result.Loading
import com.mbakgun.core.domain.model.Result.Success
import com.mbakgun.restaurants.domain.model.Restaurants
import com.mbakgun.restaurants.domain.model.SearchSortFilter

@Composable
fun RestaurantsScreen(
    result: State<Result<Restaurants>>,
    sortFilterState: State<SearchSortFilter>,
    onSetSorting: (SearchSortFilter.Sorting) -> Unit,
    refreshState: State<Boolean>,
    onRefresh: () -> Unit,
    onQueryUpdated: (String) -> Unit
) {
    when (val response = result.value) {
        is Loading -> LoadingScreen()
        is Error -> ErrorScreen(response.exception.localizedMessage)
        is Success -> RestaurantListScreen(
            restaurants = response.data.restaurants,
            sortFilterState = sortFilterState,
            onSetSorting = onSetSorting,
            refreshState = refreshState,
            onRefresh = onRefresh,
            onQueryUpdated = onQueryUpdated
        )
    }
}
