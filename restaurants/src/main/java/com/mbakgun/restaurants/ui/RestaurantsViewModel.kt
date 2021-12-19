package com.mbakgun.restaurants.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbakgun.core.di.qualifier.IoDispatcher
import com.mbakgun.core.domain.model.Result
import com.mbakgun.restaurants.domain.model.Restaurants
import com.mbakgun.restaurants.domain.model.SearchSortFilter
import com.mbakgun.restaurants.domain.model.SearchSortFilter.Sorting
import com.mbakgun.restaurants.domain.usecase.RestaurantsFetchUseCase
import com.mbakgun.restaurants.domain.usecase.RestaurantsSortUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class RestaurantsViewModel @Inject constructor(
    private val restaurantsFetchUseCase: RestaurantsFetchUseCase,
    private val restaurantsSortUseCase: RestaurantsSortUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val refreshState = mutableStateOf(false)

    val restaurantsFlow: StateFlow<Result<Restaurants>> = flow {
        restaurantsFetchUseCase.fetchRestaurants()
            .combine(restaurantsSortUseCase.getSortFilterFlow()) { restaurants, filter ->
                restaurantsSortUseCase.setRestaurantsResult(restaurants)
                filter
            }.collect {
                emitAll(restaurantsSortUseCase.sortRestaurants())
            }
    }.flowOn(ioDispatcher).stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        Result.Loading
    )

    fun getRefreshState(): State<Boolean> = refreshState

    fun getSortStateFlow(): Flow<SearchSortFilter> = restaurantsSortUseCase.getSortFilterFlow()

    fun setQuery(query: String) = restaurantsSortUseCase.setQuery(query)

    fun setSorting(sorting: Sorting) = restaurantsSortUseCase.setSorting(sorting)

    fun refresh() {
        refreshState.value = true
        restaurantsSortUseCase.refresh()
        refreshState.value = false
    }
}
