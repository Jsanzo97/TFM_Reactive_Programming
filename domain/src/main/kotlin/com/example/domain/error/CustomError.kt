package com.example.domain.error

/**
 * Represent errors in the system
 */
sealed class CustomError

object IOOperationError : CustomError()
object UnknownIOError : CustomError()