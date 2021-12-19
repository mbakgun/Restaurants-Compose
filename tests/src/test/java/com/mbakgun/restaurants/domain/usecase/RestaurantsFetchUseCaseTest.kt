package com.mbakgun.restaurants.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mbakgun.core.domain.model.Result
import com.mbakgun.restaurants.RestaurantsFactory
import com.mbakgun.restaurants.data.RestaurantsRepository
import com.mbakgun.restaurants.domain.mapper.RestaurantsMapper
import com.mbakgun.restaurants.domain.model.Restaurants
import com.mbakgun.util.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class RestaurantsFetchUseCaseTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var restaurantsRepository: RestaurantsRepository

    private lateinit var fetchUseCase: RestaurantsFetchUseCase

    @Before
    fun setUp() {

        fetchUseCase = RestaurantsFetchUseCase(
            restaurantsRepository = restaurantsRepository,
            restaurantsMapper = RestaurantsMapper(),
            ioDispatcher = coroutineTestRule.testDispatcher
        )
    }

    @Test
    fun `when fetchRestaurants requested then loading should be propagated`() =
        coroutineTestRule.testScope.runTest {
            val result: Result<Restaurants> = fetchUseCase.fetchRestaurants().first()

            assert(result is Result.Loading)
        }

    @Test
    fun `when fetchRestaurants gets error then it should be observed`() =
        coroutineTestRule.testScope.runTest {
            `when`(restaurantsRepository.fetchRestaurants()).thenReturn(null)

            val result: Result<Restaurants> = fetchUseCase.fetchRestaurants().last()

            assert(
                result is Result.Error &&
                        result.exception is NullPointerException
            )
        }

    @Test
    fun `when fetchRestaurants succeeded then flow should observe domain model`() =
        coroutineTestRule.testScope.runTest {
            `when`(restaurantsRepository.fetchRestaurants()).thenReturn(
                flowOf(
                    RestaurantsFactory.getMockRestaurantsDto()
                )
            )

            val result: Result<Restaurants> = fetchUseCase.fetchRestaurants().last()

            assert(
                result is Result.Success &&
                        result.data.restaurants.isNotEmpty()
            )
        }
}
