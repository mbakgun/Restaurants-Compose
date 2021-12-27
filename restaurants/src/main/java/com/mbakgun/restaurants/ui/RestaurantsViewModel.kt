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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class RestaurantsViewModel @Inject constructor(
    private val restaurantsFetchUseCase: RestaurantsFetchUseCase,
    private val restaurantsSortUseCase: RestaurantsSortUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val refreshState = MutableStateFlow(false)

    val restaurantsFlow: StateFlow<Result<Restaurants>> = channelFlow {
        val fetchRestaurantsFlow: Flow<Result<Restaurants>> =
            restaurantsFetchUseCase
                .fetchRestaurants()

        val sortFilterFlow: Flow<Result<Restaurants>> =
            restaurantsSortUseCase
                .getSortFilterFlow()
                .flatMapLatest {
                    restaurantsSortUseCase.sortRestaurants()
                }

        val refreshFlow: Flow<Result<Restaurants>> =
            refreshState
                .filter { it }
                .flatMapLatest {
                    restaurantsFetchUseCase
                        .fetchRestaurants()
                        .onCompletion {
                            refreshState.tryEmit(false)
                        }
                }

        merge(fetchRestaurantsFlow, sortFilterFlow, refreshFlow)
            .collect {
                restaurantsSortUseCase.setRestaurantsResult(it)
                send(it)
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
