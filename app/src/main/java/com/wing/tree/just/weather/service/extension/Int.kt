package com.wing.tree.just.weather.service.extension

import com.wing.tree.just.weather.service.constant.TWO

val Int.float get() = toFloat()
val Int.half get() = div(TWO)