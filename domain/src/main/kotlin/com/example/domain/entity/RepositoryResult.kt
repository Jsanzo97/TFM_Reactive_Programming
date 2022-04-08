package com.example.domain.entity

import com.example.domain.error.CustomError
import com.example.domain.error.UnknownIOError

abstract class RepositoryResult

data class RepositoryErrorOperationResult(
    val error: CustomError = UnknownIOError
): RepositoryResult()

data class RepositorySuccessOperationResult(
    val data: Any
): RepositoryResult()

