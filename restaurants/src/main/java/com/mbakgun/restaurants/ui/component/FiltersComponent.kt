package com.mbakgun.restaurants.ui.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mbakgun.core.compose.VeryDarkViolet
import com.mbakgun.restaurants.R
import com.mbakgun.restaurants.R.string.average_price
import com.mbakgun.restaurants.R.string.best_match
import com.mbakgun.restaurants.R.string.delivery_cost
import com.mbakgun.restaurants.R.string.distance
import com.mbakgun.restaurants.R.string.minimum_cost
import com.mbakgun.restaurants.R.string.newest
import com.mbakgun.restaurants.R.string.popularity
import com.mbakgun.restaurants.R.string.rating
import com.mbakgun.restaurants.domain.model.SearchSortFilter
import com.mbakgun.restaurants.domain.model.SearchSortFilter.Sorting

@Composable
@Suppress("LongMethod")
fun FiltersComponent(
    sortFilter: State<SearchSortFilter>,
    onSortingChanged: (Sorting) -> Unit
) = Column(
    Modifier
        .fillMaxWidth()
        .height(360.dp)
        .padding(16.dp)
) {

    Text(
        text = stringResource(R.string.select_sorting),
        color = VeryDarkViolet,
        style = MaterialTheme.typography.h2,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )

    Divider()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.opening_state),
            color = VeryDarkViolet,
            style = MaterialTheme.typography.h3
        )

        Switch(
            checked = sortFilter.value.sorting.isSortByStatus,
            onCheckedChange = {
                onSortingChanged.invoke(
                    sortFilter.value.sorting.copy(isSortByStatus = it)
                )
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 8.dp),
    ) {

        FilterItem(
            stringResource(best_match),
            sortFilter.value.sorting.isSortByBestMatch
        ) {
            onSortingChanged(
                Sorting(
                    isSortByStatus = sortFilter.value.sorting.isSortByStatus,
                    isSortByBestMatch = it
                )
            )
        }

        FilterItem(
            stringResource(newest),
            sortFilter.value.sorting.isSortByNewest
        ) {
            onSortingChanged(
                Sorting(
                    isSortByStatus = sortFilter.value.sorting.isSortByStatus,
                    isSortByNewest = it
                )
            )
        }

        FilterItem(
            stringResource(rating),
            sortFilter.value.sorting.isSortByRating
        ) {
            onSortingChanged(
                Sorting(
                    isSortByStatus = sortFilter.value.sorting.isSortByStatus,
                    isSortByRating = it
                )
            )
        }

        FilterItem(
            stringResource(distance),
            sortFilter.value.sorting.isSortByDistance
        ) {
            onSortingChanged(
                Sorting(
                    isSortByStatus = sortFilter.value.sorting.isSortByStatus,
                    isSortByDistance = it
                )
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 8.dp),
    ) {

        FilterItem(
            stringResource(popularity),
            sortFilter.value.sorting.isSortByPopularity
        ) {
            onSortingChanged(
                Sorting(
                    isSortByStatus = sortFilter.value.sorting.isSortByStatus,
                    isSortByPopularity = it
                )
            )
        }

        FilterItem(
            stringResource(average_price),
            sortFilter.value.sorting.isSortByAveragePrice
        ) {
            onSortingChanged(
                Sorting(
                    isSortByStatus = sortFilter.value.sorting.isSortByStatus,
                    isSortByAveragePrice = it
                )
            )
        }

        FilterItem(
            stringResource(delivery_cost),
            sortFilter.value.sorting.isSortByDeliveryCost
        ) {
            onSortingChanged(
                Sorting(
                    isSortByStatus = sortFilter.value.sorting.isSortByStatus,
                    isSortByDeliveryCost = it
                )
            )
        }

        FilterItem(
            stringResource(minimum_cost),
            sortFilter.value.sorting.isSortByMinimumCost
        ) {
            onSortingChanged(
                Sorting(
                    isSortByStatus = sortFilter.value.sorting.isSortByStatus,
                    isSortByMinimumCost = it
                )
            )
        }
    }
}
