package com.mbakgun.restaurants.data.local.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class RestaurantsDto(
    val restaurants: List<RestaurantDto>?
) {
    @Keep
    @Serializable
    data class RestaurantDto(
        val id: String,
        val name: String,
        val sortingValues: SortingValuesDto,
        val imageUrl: String,
        val description: String?,
        val status: String?
    ) {
        @Keep
        @Serializable
        data class SortingValuesDto(
            val averageProductPrice: Int,
            val bestMatch: Double,
            val deliveryCosts: Int,
            val distance: Int,
            val minCost: Int,
            val newest: Double,
            val popularity: Double,
            val ratingAverage: Double
        )
    }
}
