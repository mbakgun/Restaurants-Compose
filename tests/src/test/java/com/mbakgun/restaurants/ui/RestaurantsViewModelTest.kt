package com.mbakgun.restaurants.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mbakgun.restaurants.RestaurantsFactory
import com.mbakgun.restaurants.domain.usecase.RestaurantsFetchUseCase
import com.mbakgun.restaurants.domain.usecase.RestaurantsSortUseCase
import com.mbakgun.util.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class RestaurantsViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var restaurantsFetchUseCase: RestaurantsFetchUseCase

    @Mock
    private lateinit var restaurantsSortUseCase: RestaurantsSortUseCase

    private lateinit var viewModel: RestaurantsViewModel

    @Test
    fun `when flow updated then result should contains value`() =
        coroutineTestRule.testScope.runTest {
            val data = RestaurantsFactory.getMockRestaurantResult()

            `when`(restaurantsSortUseCase.getSortFilterFlow())
                .thenReturn(emptyFlow())

            `when`(restaurantsFetchUseCase.fetchRestaurants())
                .thenReturn(flowOf(data))

            viewModel = RestaurantsViewModel(
                restaurantsFetchUseCase = restaurantsFetchUseCase,
                restaurantsSortUseCase = restaurantsSortUseCase,
                ioDispatcher = coroutineTestRule.testDispatcher
            )
            val job = launch {
                viewModel.restaurantsFlow.last().also {
                    assertEquals(data, it)
                }
            }
            job.cancel()
        }
}
