package com.wing.tree.just.weather.service.core

import android.app.Activity
import android.content.pm.PackageManager.PERMISSION_GRANTED

const val ONE = 1
const val TWO = 2

val Int.float get() = toFloat()
val Int.half get() = div(TWO)

fun Activity.checkPermission(vararg permission: String): Boolean {
    return permission.all { checkSelfPermission(it) == PERMISSION_GRANTED }
}

fun Activity.shouldShowRequestPermissionRationale(vararg permission: String): Boolean {
    return permission.any { shouldShowRequestPermissionRationale(it) }
}

