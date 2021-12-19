package com.mbakgun.restaurants.domain.usecase

import com.mbakgun.core.di.qualifier.IoDispatcher
import com.mbakgun.core.domain.model.Result
import com.mbakgun.core.domain.model.Result.Success
import com.mbakgun.restaurants.data.RestaurantsRepository
import com.mbakgun.restaurants.domain.mapper.RestaurantsMapper
import com.mbakgun.restaurants.domain.model.Restaurants
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class RestaurantsFetchUseCase @Inject constructor(
    private val restaurantsRepository: RestaurantsRepository,
    private val restaurantsMapper: RestaurantsMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun fetchRestaurants(): Flow<Result<Restaurants>> = flow {
        emit(Result.Loading)

        emitAll(restaurantsRepository.fetchRestaurants().map {
            Success(restaurantsMapper.mapToRestaurants(it))
        })
    }.catch {
        emit(Result.Error(it))
    }.onEach {
        Timber.d(it.toString())
    }.flowOn(ioDispatcher)

}
