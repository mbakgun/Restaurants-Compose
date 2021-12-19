package com.mbakgun.restaurants.ui.screen

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mbakgun.core.compose.Orange
import com.mbakgun.restaurants.domain.model.Restaurants.Restaurant
import com.mbakgun.restaurants.domain.model.SearchSortFilter
import com.mbakgun.restaurants.ui.RestaurantsViewModel
import com.mbakgun.restaurants.ui.component.FiltersComponent
import com.mbakgun.restaurants.ui.component.RestaurantList
import com.mbakgun.restaurants.ui.component.SearchableToolbar
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Suppress("LongMethod")
fun RestaurantListScreen(restaurants: List<Restaurant>) {
    val viewModel = hiltViewModel<RestaurantsViewModel>()
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val localFocusManager = LocalFocusManager.current
    val filterBottomSheetState = rememberModalBottomSheetState(Hidden)
    val activity = LocalContext.current as ComponentActivity
    val sortFilterState: State<SearchSortFilter> = viewModel
        .getSortStateFlow()
        .collectAsState(SearchSortFilter())

    BackHandler {
        if (filterBottomSheetState.isVisible) {
            coroutineScope.launch {
                filterBottomSheetState.hide()
            }
        } else activity.finish()
    }

    ModalBottomSheetLayout(
        sheetState = filterBottomSheetState,
        sheetContent = {
            FiltersComponent(sortFilterState) {
                viewModel.setSorting(it)
                coroutineScope.launch(Main) {
                    scrollState.animateScrollToItem(0)
                }
            }
        },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetElevation = 4.dp,
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(viewModel.getRefreshState().value),
            onRefresh = {
                coroutineScope.launch(Main) { scrollState.scrollToItem(0) }
                viewModel.refresh()
            }) {

            Column(modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onPress = { localFocusManager.clearFocus(true) })
                }) {

                Spacer(
                    Modifier
                        .background(Orange)
                        .statusBarsHeight()
                        .fillMaxWidth()
                )

                SearchableToolbar(
                    sortFilter = sortFilterState,
                    onQueryUpdated = viewModel::setQuery
                ) {
                    coroutineScope.launch {
                        localFocusManager.clearFocus(true)
                        filterBottomSheetState.show()
                    }
                }

                RestaurantList(restaurants, scrollState)
            }
        }
    }
}
