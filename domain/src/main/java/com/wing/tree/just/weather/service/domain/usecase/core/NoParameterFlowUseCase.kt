package com.wing.tree.just.weather.service.domain.usecase.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class NoParameterFlowUseCase <R: Any>(private val coroutineDispatcher: CoroutineDispatcher) {
    operator fun invoke(): Flow<Result<R>> = execute()
        .map <R, Result<R>> { Result.Success(it) }
        .catch { emit(Result.Failure(it)) }
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(): Flow<R>
}