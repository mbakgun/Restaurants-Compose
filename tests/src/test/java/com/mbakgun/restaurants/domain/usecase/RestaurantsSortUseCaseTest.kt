package com.mbakgun.restaurants.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mbakgun.core.domain.model.Result
import com.mbakgun.core.domain.model.data
import com.mbakgun.restaurants.RestaurantsFactory.getMockRestaurantResult
import com.mbakgun.restaurants.domain.model.Restaurants
import com.mbakgun.restaurants.domain.model.SearchSortFilter
import com.mbakgun.restaurants.domain.model.SearchSortFilter.Sorting
import com.mbakgun.util.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class RestaurantsSortUseCaseTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var sortUseCase: RestaurantsSortUseCase

    @Before
    fun setUp() {
        sortUseCase = RestaurantsSortUseCase(coroutineTestRule.testDispatcher)
    }

    @Test
    fun `when setQuery called then flow should reflect updated value`() =
        coroutineTestRule.testScope.runTest {
            val value = "Jetpack"

            sortUseCase.setQuery(value)

            val job = launch {
                sortUseCase.getSortFilterFlow().collect {
                    Assert.assertEquals(
                        it.filterQuery,
                        value
                    )
                }
            }

            job.cancel()
        }

    @Test
    fun `when setSorting called then flow should reflect updated value`() =
        coroutineTestRule.testScope.runTest {
            val value = Sorting(true)

            sortUseCase.setSorting(value)

            val job = launch {
                sortUseCase.getSortFilterFlow().collectLatest {
                    Assert.assertEquals(
                        it.sorting,
                        value
                    )
                }
            }

            job.cancel()
        }

    @Test
    fun `when refresh called then flow should reflect empty SearchSortFilter value`() =
        coroutineTestRule.testScope.runTest {
            val value = Sorting(true)

            sortUseCase.setSorting(value)
            sortUseCase.refresh()

            val job = launch {
                sortUseCase.getSortFilterFlow().collectLatest {
                    Assert.assertEquals(
                        SearchSortFilter(),
                        it
                    )
                }
            }

            job.cancel()
        }

    // filter name with A
    @Test
    fun `when sortRestaurants called with query then flow should return only filtered items`() =
        coroutineTestRule.testScope.runTest {
            val query = "A"
            val restaurantsResult: Result.Success<Restaurants> = getMockRestaurantResult()

            sortUseCase.setRestaurantsResult(restaurantsResult)
            sortUseCase.setQuery(query)

            sortUseCase.sortRestaurants().collectLatest {
                val restaurants = it.data?.restaurants ?: fail("restaurants are null")

                assert(restaurants.size == 1)
                assert(restaurants.first().name == query)
            }
        }

    // no filter no sorting - sorts by id (backend ordering)
    @Test
    fun `when sortRestaurants called without query or sortingValues then flow should return sorted by id`() =
        coroutineTestRule.testScope.runTest {
            val restaurantsResult: Result.Success<Restaurants> = getMockRestaurantResult()

            sortUseCase.setRestaurantsResult(restaurantsResult)

            val job = launch {
                sortUseCase.sortRestaurants().last().also {
                    val restaurants = it.data?.restaurants ?: fail("restaurants are null")

                    assertThat(restaurants)
                        .isSortedAccordingTo(compareBy(Restaurants.Restaurant::id))
                }
            }

            job.cancel()
        }

    // sort by opening status
    @Test
    fun `when sortRestaurants called with sortByStatus then flow should return sorted by status priority`() =
        coroutineTestRule.testScope.runTest {
            val restaurantsResult: Result.Success<Restaurants> = getMockRestaurantResult()

            sortUseCase.setRestaurantsResult(restaurantsResult)
            sortUseCase.setSorting(Sorting(true))

            sortUseCase.sortRestaurants().collectLatest {
                val restaurants = it.data?.restaurants ?: fail("restaurants are null")

                assertThat(restaurants)
                    .isSortedAccordingTo(compareBy { restaurant ->
                        restaurant.status.priority
                    })
            }
        }

    // sort by only sortingValue(newest)
    @Test
    fun `when sortRestaurants called with sortByNewest then flow should return sorted by newest`() =
        coroutineTestRule.testScope.runTest {
            val restaurantsResult: Result.Success<Restaurants> = getMockRestaurantResult()

            sortUseCase.setRestaurantsResult(restaurantsResult)
            sortUseCase.setSorting(Sorting(isSortByNewest = true))

            sortUseCase.sortRestaurants().collectLatest {
                val restaurants = it.data?.restaurants ?: fail("restaurants are null")

                assertThat(restaurants)
                    .isSortedAccordingTo(compareByDescending { restaurant ->
                        restaurant.sortingValues.newest
                    })
            }
        }

    // filter and sort with open status
    @Test
    fun `when filter and status combined then flow should return filtered and sorted list by status priority`() =
        coroutineTestRule.testScope.runTest {
            val restaurantsResult: Result.Success<Restaurants> = getMockRestaurantResult()
            val query = "B"

            sortUseCase.setRestaurantsResult(restaurantsResult)
            sortUseCase.setQuery(query)
            sortUseCase.setSorting(Sorting(true))

            sortUseCase.sortRestaurants().collectLatest {
                val restaurants = it.data?.restaurants ?: fail("restaurants are null")

                assert(restaurants.size == 2)

                assertThat(restaurants)
                    .isSortedAccordingTo(compareBy { restaurant ->
                        restaurant.status.priority
                    })
            }
        }

    // filter and sort with sortingValue(newest)
    @Test
    fun `when filter and sortingValue then flow should return filtered and sorted list by given sortingValue`() =
        coroutineTestRule.testScope.runTest {
            val restaurantsResult: Result.Success<Restaurants> = getMockRestaurantResult()
            val query = "B"

            sortUseCase.setRestaurantsResult(restaurantsResult)
            sortUseCase.setQuery(query)
            sortUseCase.setSorting(Sorting(isSortByNewest = true))

            sortUseCase.sortRestaurants().collectLatest {
                val restaurants = it.data?.restaurants ?: fail("restaurants are null")

                assert(restaurants.size == 2)

                assertThat(restaurants)
                    .isSortedAccordingTo(compareByDescending { restaurant ->
                        restaurant.sortingValues.newest
                    })
            }
        }

    // heaven (filter - open state sorting - newest sorting)
    @Test
    fun `when all possibilities combined then flow should return filtered and sorted list`() =
        coroutineTestRule.testScope.runTest {
            val restaurantsResult: Result.Success<Restaurants> = getMockRestaurantResult()
            val query = "B"

            sortUseCase.setRestaurantsResult(restaurantsResult)
            sortUseCase.setQuery(query)
            sortUseCase.setSorting(Sorting(isSortByStatus = true, isSortByNewest = true))

            sortUseCase.sortRestaurants().collectLatest {
                val restaurants = it.data?.restaurants ?: fail("restaurants are null")

                assert(restaurants.size == 2)

                assertThat(restaurants)
                    .isSortedAccordingTo(compareBy { restaurant ->
                        restaurant.status.priority
                    })

                assertThat(restaurants)
                    .isSortedAccordingTo(compareByDescending { restaurant ->
                        restaurant.sortingValues.newest
                    })
            }
        }

    @Test
    fun `when restaurants has not set yet flow should return empty data`() =
        coroutineTestRule.testScope.runTest {
            sortUseCase.setSorting(Sorting(true))

            sortUseCase.sortRestaurants().collectLatest {
                val restaurants = it.data?.restaurants ?: fail("restaurants are null")

                assert(restaurants.isEmpty())
            }
        }

}
