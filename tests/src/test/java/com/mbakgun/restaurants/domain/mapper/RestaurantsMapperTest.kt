package com.mbakgun.restaurants.domain.mapper

import com.mbakgun.restaurants.RestaurantsFactory
import com.mbakgun.restaurants.data.local.model.RestaurantsDto
import com.mbakgun.restaurants.domain.model.Status
import org.junit.Assert
import org.junit.Test

class RestaurantsMapperTest {

    private val mapper by lazy(::RestaurantsMapper)

    @Test
    fun `mapToRestaurants should map dto to domain model`() {
        val dto = RestaurantsFactory.getMockRestaurantsDto()

        val restaurants = mapper.mapToRestaurants(dto)

        assert(restaurants.restaurants.isNotEmpty())
    }

    @Test
    fun `mapToRestaurants should map null restaurants to empty domain model`() {
        val dto = RestaurantsDto(null)

        val restaurants = mapper.mapToRestaurants(dto)

        assert(restaurants.restaurants.isEmpty())
    }

    @Test
    fun `mapToRestaurant should map dto to domain model`() {
        val dto = RestaurantsFactory.getMockRestaurantDto()

        val restaurant = mapper.mapToRestaurant(dto)

        Assert.assertEquals(
            dto.status,
            restaurant.status.title.lowercase()
        )

        Assert.assertEquals(
            dto.id.toDouble(),
            restaurant.id,
            0.0
        )

        Assert.assertEquals(
            dto.name,
            restaurant.name
        )
    }

    @Test
    fun `mapToStatus should map known status to status enum`() {
        //given
        val givenStatus = "open"

        //when
        val status = mapper.mapToStatus(givenStatus)

        //then
        Assert.assertEquals(
            status,
            Status.OPEN
        )
    }

    @Test
    fun `mapToStatus should map null status to default(CLOSED) status`() {
        //given
        val givenStatus = null

        //when
        val status = mapper.mapToStatus(givenStatus)

        //then
        Assert.assertEquals(
            status,
            Status.CLOSED
        )
    }

    @Test
    fun `mapToStatus should map new status type to default(CLOSED) status`() {
        //given
        val givenStatus = "o p e n"

        //when
        val status = mapper.mapToStatus(givenStatus)

        //then
        Assert.assertEquals(
            status,
            Status.CLOSED
        )
    }

    @Test
    fun `mapToSortingValue should map dto to domain model`() {
        //given
        val dto = RestaurantsFactory.getMockSortingDto()

        //when
        val sortingValue = mapper.mapToSortingValue(dto)

        //then
        Assert.assertEquals(
            dto.bestMatch,
            sortingValue.bestMatch,
            0.0
        )

        Assert.assertEquals(
            dto.deliveryCosts,
            sortingValue.deliveryCosts.toInt()
        )

        Assert.assertEquals(
            dto.distance,
            sortingValue.distance.toInt()
        )

        Assert.assertEquals(
            dto.minCost,
            sortingValue.minCost.toInt()
        )

        Assert.assertEquals(
            dto.newest,
            sortingValue.newest,
            0.0
        )

        Assert.assertEquals(
            dto.popularity,
            sortingValue.popularity,
            0.0
        )

        Assert.assertEquals(
            dto.ratingAverage,
            sortingValue.ratingAverage,
            0.0
        )

        Assert.assertEquals(
            dto.averageProductPrice,
            sortingValue.averageProductPrice.toInt()
        )
    }
}
