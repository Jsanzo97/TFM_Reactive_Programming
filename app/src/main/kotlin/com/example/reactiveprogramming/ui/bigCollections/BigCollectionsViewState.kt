package com.example.reactiveprogramming.ui.bigCollections

/**
 * View states for BigCollectionsFragment
 */
sealed class BigCollectionsViewState

object PerformingOperation: BigCollectionsViewState()

// View states related to sequences
class ExecuteNextSequenceTestCase(val previousTestCaseResult: BigCollectionsTestCaseResult): BigCollectionsViewState()
class SequenceTestCasesFinished(val testCaseResult: BigCollectionsTestCaseResult): BigCollectionsViewState()
class SequenceOperationError(val message: String): BigCollectionsViewState()

// View states related to list
class ExecuteNextListTestCase(val previousTestCaseResult: BigCollectionsTestCaseResult): BigCollectionsViewState()
class ListTestCasesFinished(val testCaseResult: BigCollectionsTestCaseResult): BigCollectionsViewState()
class ListOperationError(val message: String): BigCollectionsViewState()

