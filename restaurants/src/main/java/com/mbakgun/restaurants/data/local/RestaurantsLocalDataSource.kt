package com.mbakgun.restaurants.data.local

import com.mbakgun.restaurants.data.RestaurantsDataSource
import com.mbakgun.restaurants.data.local.model.RestaurantsDto
import javax.inject.Inject

class RestaurantsLocalDataSource @Inject constructor(
    private val restaurantsProvider: RestaurantsProvider
) : RestaurantsDataSource.Local {

    override suspend fun fetchRestaurants(): RestaurantsDto =
        restaurantsProvider.fetchRestaurants()
}
