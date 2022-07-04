package com.wing.tree.just.weather.service.data.core

import kotlin.reflect.full.memberProperties

inline fun <reified T : Any> T.asQueryMap() : QueryMap {
    val map = T::class.memberProperties.associateBy { it.name }

    return map.keys.associateWith { map[it]?.get(this).toString() }
}