package com.mbakgun.restaurants.domain.usecase

import com.mbakgun.core.di.qualifier.IoDispatcher
import com.mbakgun.core.domain.model.Result
import com.mbakgun.core.domain.model.data
import com.mbakgun.restaurants.domain.model.Restaurants
import com.mbakgun.restaurants.domain.model.SearchSortFilter
import com.mbakgun.restaurants.domain.model.SearchSortFilter.Sorting
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

class RestaurantsSortUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    private var restaurantsResult: Result<Restaurants>? = null
    private var restaurantsData: Restaurants? = null

    private val sortFilterFlow = MutableStateFlow(SearchSortFilter())

    fun getSortFilterFlow(): Flow<SearchSortFilter> = sortFilterFlow

    fun setRestaurantsResult(result: Result<Restaurants>) {
        if (restaurantsData == null) restaurantsData = result.data
        restaurantsResult = result
    }

    fun setQuery(value: String) {
        sortFilterFlow.value = sortFilterFlow.value.copy(filterQuery = value)
    }

    fun setSorting(sorting: Sorting) {
        sortFilterFlow.value = sortFilterFlow.value.copy(sorting = sorting)
    }

    fun refresh() {
        restaurantsData = null
        restaurantsResult = null
        sortFilterFlow.value = SearchSortFilter()
    }

    fun sortRestaurants(): Flow<Result<Restaurants>> = flowOf(
        restaurantsData?.let {
            Result.Success(Restaurants(it.restaurants.filter { restaurant ->
                restaurant.name.lowercase()
                    .contains(sortFilterFlow.value.filterQuery.lowercase())
            }.sortedWith(
                compareBy<Restaurants.Restaurant> { restaurant ->
                    if (sortFilterFlow.value.sorting.isSortByStatus) restaurant.status.priority
                    else 1
                }.thenByDescending { restaurant ->
                    when {
                        sortFilterFlow.value.sorting.isSortByBestMatch ->
                            restaurant.sortingValues.bestMatch
                        sortFilterFlow.value.sorting.isSortByNewest ->
                            restaurant.sortingValues.newest
                        sortFilterFlow.value.sorting.isSortByRating ->
                            restaurant.sortingValues.ratingAverage
                        sortFilterFlow.value.sorting.isSortByDistance ->
                            restaurant.sortingValues.distance
                        sortFilterFlow.value.sorting.isSortByPopularity ->
                            restaurant.sortingValues.popularity
                        sortFilterFlow.value.sorting.isSortByAveragePrice ->
                            restaurant.sortingValues.averageProductPrice
                        sortFilterFlow.value.sorting.isSortByDeliveryCost ->
                            restaurant.sortingValues.deliveryCosts
                        sortFilterFlow.value.sorting.isSortByMinimumCost ->
                            restaurant.sortingValues.minCost
                        else -> -restaurant.id
                    }
                }
            )))
        } ?: restaurantsResult ?: Result.Success(Restaurants()))
        .flowOn(ioDispatcher)
}
