package com.example.reactiveprogramming.ui.bigCollections

sealed class BigCollectionsViewState

object PerformingOperation: BigCollectionsViewState()

class ExecuteNextSequenceTestCase(val previousTestCaseResult: BigCollectionsTestCaseResult): BigCollectionsViewState()
class SequenceTestCasesFinished(val testCaseResult: BigCollectionsTestCaseResult): BigCollectionsViewState()
class SequenceOperationError(val message: String): BigCollectionsViewState()

class ExecuteNextListTestCase(val previousTestCaseResult: BigCollectionsTestCaseResult): BigCollectionsViewState()
class ListTestCasesFinished(val testCaseResult: BigCollectionsTestCaseResult): BigCollectionsViewState()
class ListOperationError(val message: String): BigCollectionsViewState()

