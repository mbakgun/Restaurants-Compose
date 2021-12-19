package com.mbakgun.restaurants.data

import com.mbakgun.restaurants.data.local.model.RestaurantsDto

interface RestaurantsDataSource {

    interface Local {
        suspend fun fetchRestaurants(): RestaurantsDto
    }
}
