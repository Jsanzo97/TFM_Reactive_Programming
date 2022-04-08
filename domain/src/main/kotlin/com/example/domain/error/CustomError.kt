package com.example.domain.error

sealed class CustomError

object IOOperationError : CustomError()
object UnknownIOError : CustomError()