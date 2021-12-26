package com.mbakgun.restaurants

import com.mbakgun.core.domain.model.Result
import com.mbakgun.restaurants.data.local.model.RestaurantsDto
import com.mbakgun.restaurants.data.local.model.RestaurantsDto.RestaurantDto
import com.mbakgun.restaurants.data.local.model.RestaurantsDto.RestaurantDto.SortingValuesDto
import com.mbakgun.restaurants.domain.model.Restaurants
import com.mbakgun.restaurants.domain.model.Restaurants.Restaurant
import com.mbakgun.restaurants.domain.model.Restaurants.Restaurant.SortingValues
import com.mbakgun.restaurants.domain.model.Status

object RestaurantsFactory {

    fun getMockRestaurantsDto() = RestaurantsDto(
        restaurants = listOf(getMockRestaurantDto())
    )

    fun getMockRestaurantDto() = RestaurantDto(
        id = "1",
        name = "Castle",
        sortingValues = getMockSortingDto(),
        status = "closed",
        imageUrl = "",
        description = null
    )

    fun getMockSortingDto() = SortingValuesDto(
        averageProductPrice = 1,
        bestMatch = 5.0,
        deliveryCosts = 0,
        distance = 0,
        minCost = 3,
        newest = 4.5,
        popularity = 0.0,
        ratingAverage = 0.0
    )

    fun getMockRestaurantResult() = Result.Success(
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
                ),
                Restaurant(
                    id = 2.0,
                    name = "B",
                    sortingValues = SortingValues(
                        averageProductPrice = 0.0,
                        bestMatch = 0.0,
                        deliveryCosts = 0.0,
                        distance = 0.0,
                        minCost = 0.0,
                        newest = 2.0,
                        popularity = 0.0,
                        ratingAverage = 0.0
                    ),
                    status = Status.CLOSED
                ),
                Restaurant(
                    id = 3.0,
                    name = "B",
                    sortingValues = SortingValues(
                        averageProductPrice = 0.0,
                        bestMatch = 0.0,
                        deliveryCosts = 0.0,
                        distance = 0.0,
                        minCost = 0.0,
                        newest = 3.0,
                        popularity = 0.0,
                        ratingAverage = 0.0
                    ),
                    status = Status.ORDER_AHEAD
                )
            )
        )
    )
}
