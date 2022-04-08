package com.example.reactiveprogramming.ui.home

sealed class HomeViewState

object InitializingDatabase: HomeViewState()
object InitializedDatabase: HomeViewState()
class ErrorInOperation(val message: String): HomeViewState()