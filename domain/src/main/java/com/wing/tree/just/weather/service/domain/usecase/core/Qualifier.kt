package com.wing.tree.just.weather.service.domain.usecase.core

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultCoroutineDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IOCoroutineDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainCoroutineDispatcher