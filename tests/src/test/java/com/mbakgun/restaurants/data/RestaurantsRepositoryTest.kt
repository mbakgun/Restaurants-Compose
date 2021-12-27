package com.mbakgun.restaurants.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mbakgun.restaurants.RestaurantsFactory.getMockRestaurantsDto
import com.mbakgun.restaurants.data.local.RestaurantsLocalDataSource
import com.mbakgun.restaurants.data.memory.RestaurantsMemoryDataSource
import com.mbakgun.util.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by burakakgun on 27.12.2021.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class RestaurantsRepositoryTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var restaurantsLocalDataSource: RestaurantsLocalDataSource

    @Mock
    private lateinit var restaurantsMemoryDataSource: RestaurantsMemoryDataSource

    private lateinit var restaurantsRepository: RestaurantsRepository

    @Before
    fun setUp() {
        restaurantsRepository = RestaurantsRepository(
            restaurantsLocalDataSource = restaurantsLocalDataSource,
            restaurantsMemoryDataSource = restaurantsMemoryDataSource,
            ioDispatcher = coroutineTestRule.testDispatcher
        )
    }

    @Test
    fun `when cache exist then flow emitData from memoryDataSource`() =
        coroutineTestRule.testScope.runTest {
            val cache = getMockRestaurantsDto()
            `when`(restaurantsMemoryDataSource.fetchRestaurants())
                .thenReturn(cache)

            val job = launch {
                restaurantsRepository.fetchRestaurants().collect {
                    Assert.assertEquals(it, cache)
                    verify(
                        restaurantsLocalDataSource,
                        never()
                    ).fetchRestaurants()
                }
            }

            job.cancel()
        }

    @Test
    fun `when cache doesn't exist then flow emitData from localDataSource and cacheInMemory`() =
        coroutineTestRule.testScope.runTest {
            val restaurants = getMockRestaurantsDto()
            `when`(restaurantsLocalDataSource.fetchRestaurants())
                .thenReturn(restaurants)

            val job = launch {
                restaurantsRepository.fetchRestaurants().collect {
                    Assert.assertEquals(it, restaurants)
                    verify(
                        restaurantsMemoryDataSource,
                        times(1)
                    ).cacheInMemory(restaurants)
                }
            }

            job.cancel()
        }
}
