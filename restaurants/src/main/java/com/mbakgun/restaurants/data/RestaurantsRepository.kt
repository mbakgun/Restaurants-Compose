package com.mbakgun.restaurants.data

import com.mbakgun.core.di.qualifier.IoDispatcher
import com.mbakgun.restaurants.data.local.RestaurantsLocalDataSource
import com.mbakgun.restaurants.data.local.model.RestaurantsDto
import com.mbakgun.restaurants.data.memory.RestaurantsMemoryDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RestaurantsRepository @Inject constructor(
    private val restaurantsLocalDataSource: RestaurantsLocalDataSource,
    private val restaurantsMemoryDataSource: RestaurantsMemoryDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun fetchRestaurants(): Flow<RestaurantsDto> = channelFlow {
        restaurantsMemoryDataSource
            .fetchRestaurants()
            ?.let {
                send(it)
                close()
            }

        withContext(ioDispatcher) {
            launch {
                trySend(
                    restaurantsLocalDataSource
                        .fetchRestaurants()
                        .also(restaurantsMemoryDataSource::cacheInMemory)
                )
            }
        }
    }
}
