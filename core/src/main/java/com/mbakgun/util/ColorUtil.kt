package com.mbakgun.util

import android.graphics.Color

@Suppress("MagicNumber")
fun getRandomColor(colorRange: IntRange = 30..200) = Color.rgb(
    colorRange.random(),
    colorRange.random(),
    colorRange.random()
)
