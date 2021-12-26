package com.mbakgun.restaurants

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.mbakgun.core.domain.model.Result
import com.mbakgun.core.domain.model.Result.Success
import com.mbakgun.restaurants.domain.model.Restaurants
import com.mbakgun.restaurants.domain.model.Restaurants.Restaurant
import com.mbakgun.restaurants.domain.model.Restaurants.Restaurant.SortingValues
import com.mbakgun.restaurants.domain.model.SearchSortFilter
import com.mbakgun.restaurants.domain.model.Status
import com.mbakgun.restaurants.ui.screen.RestaurantsScreen

@Suppress("TestFunctionName")
object RestaurantsFactory {

    @Composable
    fun RestaurantsTestScreen(
        result: State<Result<Restaurants>>,
        sortFilterState: State<SearchSortFilter> = mutableStateOf(SearchSortFilter()),
        refreshState: State<Boolean> = mutableStateOf(false)
    ) = RestaurantsScreen(
        result = result,
        sortFilterState = sortFilterState,
        onSetSorting = {},
        refreshState = refreshState,
        onRefresh = {},
        onQueryUpdated = {}
    )

    fun getMockRestaurantResult(): MutableState<Success<Restaurants>> =
        mutableStateOf(
            Success(
                Restaurants(
                    restaurants = listOf(
                        Restaurant(
                            id = 1.0,
                            name = "A",
                            sortingValues = SortingValues(
                                averageProductPrice = 0.0,
                                bestMatch = 0.0,
                                deliveryCosts = 0.0,
                                distance = 0.0,
                                minCost = 0.0,
                                newest = 1.0,
                                popularity = 0.0,
                                ratingAverage = 0.0
                            ),
                            status = Status.OPEN
                        )
                    )
                )
            )
        )
}
