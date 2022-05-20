package com.example.data.error

import com.example.domain.error.IOOperationError
import com.example.domain.error.UnknownIOError

/**
 * Represent the possible error from local source data
 */
sealed class LocalDataError

object WritingError : LocalDataError()
object ReadingError : LocalDataError()
object UnknownError: LocalDataError()

/**
 * Convert LocalDataError to CustomError
 * @return new CustomError depending of LocalDataError type
 */
fun LocalDataError.toCustomError() = when (this) {
    WritingError -> IOOperationError
    ReadingError -> IOOperationError
    UnknownError -> UnknownIOError
}