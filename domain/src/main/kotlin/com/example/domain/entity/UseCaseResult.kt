package com.example.domain.entity

import com.example.domain.error.CustomError
import com.example.domain.error.UnknownIOError

abstract class UseCaseResult

data class UseCaseErrorOperationResult(
    val error: CustomError = UnknownIOError
): UseCaseResult()

data class UseCaseSuccessOperationResult(
    val data: Any
): UseCaseResult()

fun RepositoryResult.toUseCaseResult(): UseCaseResult {
    return when (this) {
        is RepositorySuccessOperationResult -> {
            UseCaseSuccessOperationResult(this.data)
        }
        is RepositoryErrorOperationResult -> {
            UseCaseErrorOperationResult(this.error)
        }
        else -> {
            UseCaseErrorOperationResult()
        }
    }
}