package com.example.reactiveprogramming.ui.bigCollections

sealed class BigCollectionsViewState

object PerformingOperation: BigCollectionsViewState()
class SequenceOperationFinished(val elements: List<Int>, val timeNeeded: Double): BigCollectionsViewState()
class ListOperationFinished(val elements: List<Int>, val timeNeeded: Double): BigCollectionsViewState()

