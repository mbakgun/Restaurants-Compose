package com.mbakgun.restaurants.domain.model

data class SearchSortFilter(
    val sorting: Sorting = Sorting(),
    val filterQuery: String = "",
) {
    data class Sorting(
        val isSortByStatus: Boolean = false,
        val isSortByBestMatch: Boolean = false,
        val isSortByNewest: Boolean = false,
        val isSortByRating: Boolean = false,
        val isSortByDistance: Boolean = false,
        val isSortByPopularity: Boolean = false,
        val isSortByAveragePrice: Boolean = false,
        val isSortByDeliveryCost: Boolean = false,
        val isSortByMinimumCost: Boolean = false,
    )
}
