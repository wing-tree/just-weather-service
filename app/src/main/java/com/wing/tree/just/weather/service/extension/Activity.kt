package com.wing.tree.just.weather.service.extension

import android.app.Activity
import android.content.pm.PackageManager

fun Activity.checkPermission(vararg permission: String): Boolean {
    return permission.all { checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }
}

fun Activity.shouldShowRequestPermissionRationale(vararg permission: String): Boolean {
    return permission.any { shouldShowRequestPermissionRationale(it) }
}