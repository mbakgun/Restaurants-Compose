package com.mbakgun.restaurants.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mbakgun.core.compose.Orange
import com.mbakgun.core.compose.VeryDarkViolet
import com.mbakgun.core.compose.White
import com.mbakgun.util.vibrateDevice

@Composable
fun FilterItem(
    text: String,
    selectedFilter: Boolean,
    onSortingChanged: (Boolean) -> Unit
) {
    val context = LocalContext.current

    val color by animateColorAsState(
        targetValue = if (selectedFilter) VeryDarkViolet else Orange,
        animationSpec = tween()
    )

    Button(
        onClick = {
            context.vibrateDevice()
            onSortingChanged.invoke(selectedFilter.not())
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color
        ),
        modifier = Modifier
            .wrapContentSize()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h5,
            color = White,
        )
    }
}
