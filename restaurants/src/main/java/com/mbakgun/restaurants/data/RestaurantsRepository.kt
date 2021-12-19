package com.mbakgun.restaurants.data

import com.mbakgun.restaurants.data.local.RestaurantsLocalDataSource
import com.mbakgun.restaurants.data.local.model.RestaurantsDto
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RestaurantsRepository @Inject constructor(
    private val restaurantsLocalDataSource: RestaurantsLocalDataSource
) {

    suspend fun fetchRestaurants(): Flow<RestaurantsDto> {
        delay(750)

        return flowOf(restaurantsLocalDataSource.fetchRestaurants())
    }
}
