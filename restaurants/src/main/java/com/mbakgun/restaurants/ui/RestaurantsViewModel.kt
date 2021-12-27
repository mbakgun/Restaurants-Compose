package com.mbakgun.restaurants.ui

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class RestaurantsViewModel @Inject constructor(
    private val restaurantsFetchUseCase: RestaurantsFetchUseCase,
    private val restaurantsSortUseCase: RestaurantsSortUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val refreshState = MutableStateFlow(false)

    var restaurantsFlow: StateFlow<Result<Restaurants>> = initRestaurantsFlow()

    init {
        viewModelScope.launch {
            refreshState.collect { isRefreshing ->
                if (isRefreshing) restaurantsFlow = initRestaurantsFlow()
            }
        }
    }

    private fun initRestaurantsFlow() = flow {
        restaurantsFetchUseCase.fetchRestaurants().onCompletion {
            refreshState.tryEmit(false)
        }.combine(restaurantsSortUseCase.getSortFilterFlow()) { restaurants, _ ->
            restaurants
        }.collect {
            restaurantsSortUseCase.setRestaurantsResult(it)
            emitAll(restaurantsSortUseCase.sortRestaurants())
        }
    }.flowOn(ioDispatcher).stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        Result.Loading
    )

    fun getRefreshState(): StateFlow<Boolean> = refreshState

    fun getSortStateFlow(): Flow<SearchSortFilter> = restaurantsSortUseCase.getSortFilterFlow()

    fun setQuery(query: String) = restaurantsSortUseCase.setQuery(query)

    fun setSorting(sorting: Sorting) = restaurantsSortUseCase.setSorting(sorting)

    fun refresh() {
        viewModelScope.launch {
            refreshState.emit(true)
            restaurantsSortUseCase.refresh()
        }
    }
}
