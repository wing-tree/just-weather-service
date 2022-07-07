package com.wing.tree.just.weather.service.constant

import com.wing.tree.just.weather.service.R
import java.util.*

internal const val ONE = 1
internal const val TWO = 2

internal const val DEGREE_SIGN = "\u00B0"

internal val icons = mapOf(
    "01d" to R.drawable.ic_01d,
    "01n" to R.drawable.ic_01n,
    "02d" to R.drawable.ic_02d,
    "02n" to R.drawable.ic_02n,
    "03d" to R.drawable.ic_03d,
    "03n" to R.drawable.ic_03n,
    "04d" to R.drawable.ic_04d,
    "04n" to R.drawable.ic_04n,
    "09d" to R.drawable.ic_09d,
    "09n" to R.drawable.ic_09n,
    "10d" to R.drawable.ic_10d,
    "10n" to R.drawable.ic_10n,
    "11d" to R.drawable.ic_11d,
    "11n" to R.drawable.ic_11n,
    "13d" to R.drawable.ic_13d,
    "13n" to R.drawable.ic_13n,
    "50d" to R.drawable.ic_50d,
    "50n" to R.drawable.ic_50n,
)

internal val today: Calendar get() = Calendar.getInstance()
internal val tomorrow: Calendar get() = today.apply { add(Calendar.DATE, ONE) }