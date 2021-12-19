package com.mbakgun.restaurants.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.mbakgun.core.compose.DarkViolet
import com.mbakgun.core.compose.Orange
import com.mbakgun.core.compose.Yellowish
import com.mbakgun.restaurants.domain.model.Restaurants.Restaurant
import com.mbakgun.restaurants.domain.model.Status.CLOSED
import com.mbakgun.restaurants.domain.model.Status.OPEN
import com.mbakgun.restaurants.domain.model.Status.ORDER_AHEAD
import com.mbakgun.util.getRandomColor

@Composable
fun RestaurantList(
    restaurants: List<Restaurant>,
    scrollState: LazyListState
) = LazyColumn(
    modifier = Modifier
        .fillMaxSize()
        .navigationBarsPadding(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    state = scrollState,
) {
    items(
        items = restaurants,
        key = Restaurant::id
    ) { entity ->
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            backgroundColor = White,
            elevation = 2.dp,
            border = BorderStroke(
                0.5.dp,
                Color(getRandomColor())
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = entity.name,
                    modifier = Modifier.padding(16.dp),
                    color = DarkViolet,
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = entity.status.title,
                    modifier = Modifier.padding(16.dp),
                    color = when (entity.status) {
                        OPEN -> Green
                        ORDER_AHEAD -> Yellowish
                        CLOSED -> Orange
                    },
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}
