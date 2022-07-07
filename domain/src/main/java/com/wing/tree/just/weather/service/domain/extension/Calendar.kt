package com.wing.tree.just.weather.service.domain.extension

import java.util.*

val Calendar.year: Int get() = get(Calendar.YEAR)
val Calendar.month: Int get() = get(Calendar.MONTH)
val Calendar.date: Int get() = get(Calendar.DATE)