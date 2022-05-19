package com.example.reactiveprogramming.ui.bigCollections

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.usecase.FindFirstNumbersInListThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits
import com.example.domain.usecase.FindFirstNumbersInSequenceThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits

/**
 * ViewModel to manage the actions performed in the BigCollectionsFragment
 * @param FindFirstNumbersInListThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits
 *  Manage the execution of test cases in lists
 * @param FindFirstNumbersInSequenceThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits
 *  Manage the execution of test cases in sequences
 */
class BigCollectionsViewModel(
    private val FindFirstNumbersInListThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits:
        FindFirstNumbersInListThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits,
    private val FindFirstNumbersInSequenceThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits:
        FindFirstNumbersInSequenceThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits
): ViewModel() {

    private val _bigCollectionsViewModelLiveData = MutableLiveData<BigCollectionsViewState>()
    val bigCollectionsViewModelLiveData: LiveData<BigCollectionsViewState> get() = _bigCollectionsViewModelLiveData

    /**
     * Execute the test case in list
     * @param numberList list of numbers to execute the test
     * @param testCase test case that will be executed with its configuration
     * @param testCaseNumber number of the test case
     */
    fun findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigits(
        numberList: List<Int>,
        testCase: BigCollectionsTestCase,
        testCaseNumber: Int
    ) {
        _bigCollectionsViewModelLiveData.postValue(PerformingOperation)

        AsyncTask.execute {
            val result =
                FindFirstNumbersInListThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits(
                    numberList,
                    testCase.maxNumberToEvaluate,
                    testCase.numbersToTake,
                    testCase.maxNumberLength
                )

            if (testCaseNumber < defaultTestCases.size - 1) {
                _bigCollectionsViewModelLiveData.postValue(
                    ExecuteNextListTestCase(
                        BigCollectionsTestCaseResult(
                            result.time,
                            result.elements
                        )
                    )
                )
            } else {
                _bigCollectionsViewModelLiveData.postValue(
                    ListTestCasesFinished(
                        BigCollectionsTestCaseResult(
                            result.time,
                            result.elements
                        )
                    )
                )
            }
        }
    }

    /**
     * Manage errors in list operations
     * @param e error produced
     */
    fun errorInListOperation(e: Throwable) {
        _bigCollectionsViewModelLiveData.postValue(ListOperationError(e.toString()))
    }

    /**
     * Execute the test case in sequence
     * @param numberSequence sequence of numbers to execute the test
     * @param testCase test case that will be executed with its configuration
     * @param testCaseNumber number of the test case
     */
    fun findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
        numberSequence: Sequence<Int>,
        testCase: BigCollectionsTestCase,
        testCaseNumber: Int
    ) {
        _bigCollectionsViewModelLiveData.postValue(PerformingOperation)

        AsyncTask.execute {
            val result =
                FindFirstNumbersInSequenceThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits(
                    numberSequence,
                    testCase.maxNumberToEvaluate,
                    testCase.numbersToTake,
                    testCase.maxNumberLength
                )

            if (testCaseNumber < defaultTestCases.size - 1) {
                _bigCollectionsViewModelLiveData.postValue(
                    ExecuteNextSequenceTestCase(
                        BigCollectionsTestCaseResult(
                            result.time,
                            result.elements
                        )
                    )
                )
            } else {
                _bigCollectionsViewModelLiveData.postValue(
                    SequenceTestCasesFinished(
                        BigCollectionsTestCaseResult(
                            result.time,
                            result.elements
                        )
                    )
                )
            }
        }
    }

    /**
     * Manage errors in sequence operations
     * @param e error produced
     */
    fun errorInSequenceOperation(e: Throwable) {
        _bigCollectionsViewModelLiveData.postValue(SequenceOperationError(e.toString()))
    }
}