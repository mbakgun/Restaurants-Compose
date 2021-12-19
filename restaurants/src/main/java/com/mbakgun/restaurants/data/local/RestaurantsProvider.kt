package com.mbakgun.restaurants.data.local

import android.content.Context
import com.mbakgun.core.di.qualifier.IoDispatcher
import com.mbakgun.restaurants.data.local.model.RestaurantsDto
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader

class RestaurantsProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun fetchRestaurants(): RestaurantsDto = withContext(ioDispatcher) {
        Json.decodeFromString(
            context.assets
                .open("restaurants.json")
                .bufferedReader()
                .use(BufferedReader::readText)
        )
    }
}
