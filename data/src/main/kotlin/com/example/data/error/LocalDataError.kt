package com.example.data.error

import com.example.domain.error.IOOperationError
import com.example.domain.error.UnknownIOError

sealed class LocalDataError

object WritingError : LocalDataError()
object ReadingError : LocalDataError()
object UnknownError: LocalDataError()

fun LocalDataError.toCustomError() = when (this) {
    WritingError -> IOOperationError
    ReadingError -> IOOperationError
    UnknownError -> UnknownIOError
}