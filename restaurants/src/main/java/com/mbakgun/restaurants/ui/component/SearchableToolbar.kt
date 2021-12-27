package com.mbakgun.restaurants.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction.Companion.Done
import androidx.compose.ui.unit.dp
import com.mbakgun.core.compose.DarkViolet
import com.mbakgun.core.compose.Orange
import com.mbakgun.core.compose.White
import com.mbakgun.restaurants.R
import com.mbakgun.restaurants.R.string.filter_restaurant_name
import com.mbakgun.restaurants.R.string.sort
import com.mbakgun.restaurants.domain.model.SearchSortFilter
import com.mbakgun.util.noRippleClickable

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun SearchableToolbar(
    sortFilter: State<SearchSortFilter>,
    onQueryUpdated: (String) -> Unit,
    onFilterClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
            .clip(RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp))
            .background(Orange),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        TextField(
            value = sortFilter.value.filterQuery,
            onValueChange = {
                onQueryUpdated.invoke(it)
            },
            keyboardOptions = KeyboardOptions(imeAction = Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            textStyle = MaterialTheme.typography.h4,
            placeholder = {
                Text(
                    text = stringResource(id = filter_restaurant_name),
                    style = MaterialTheme.typography.h4
                )
            },
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(54.dp)
                .testTag(stringResource(R.string.input)),
            colors = TextFieldDefaults.textFieldColors(
                textColor = DarkViolet,
                backgroundColor = White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
        )

        Icon(
            imageVector = Icons.Filled.FilterList,
            contentDescription = stringResource(id = sort),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .noRippleClickable(onFilterClicked::invoke),
            tint = White
        )
    }
}
