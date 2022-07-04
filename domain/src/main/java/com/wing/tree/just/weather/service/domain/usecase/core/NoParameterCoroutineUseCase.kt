package com.wing.tree.just.weather.service.domain.usecase.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class NoParameterCoroutineUseCase<R: Any>(private val coroutineDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(): Result<R> {
        return try {
            withContext(coroutineDispatcher) {
                Result.Success(execute())
            }
        } catch (t: Throwable) {
            Result.Failure(t)
        }
    }

    protected abstract suspend fun execute(): R
}