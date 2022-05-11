package com.example.reactiveprogramming.ui.bigCollections

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.usecase.FindFirstNumbersInListThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits
import com.example.domain.usecase.FindFirstNumbersInSequenceThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits

class BigCollectionsViewModel(
    private val FindFirstNumbersInListThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits:
        FindFirstNumbersInListThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits,
    private val FindFirstNumbersInSequenceThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits:
        FindFirstNumbersInSequenceThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits
): ViewModel() {

    private val _bigCollectionsViewModelLiveData = MutableLiveData<BigCollectionsViewState>()
    val bigCollectionsViewModelLiveData: LiveData<BigCollectionsViewState> get() = _bigCollectionsViewModelLiveData

    fun findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigits(
        numberList: List<Int>,
        testCase: BigCollectionsTestCase,
        testCaseNumber: Int
    ) {
        _bigCollectionsViewModelLiveData.postValue(PerformingOperation)

        if (testCaseNumber < defaultTestCases.size - 1) {
            AsyncTask.execute {
                val result =
                    FindFirstNumbersInListThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits(
                        numberList,
                        testCase.maxNumberToEvaluate,
                        testCase.numbersToTake,
                        testCase.maxNumberLength
                    )

                _bigCollectionsViewModelLiveData.postValue(
                    ExecuteNextListTestCase(
                        BigCollectionsTestCaseResult(
                            result.time,
                            result.elements
                        )
                    )
                )
            }
        } else {
            AsyncTask.execute {
                val result =
                    FindFirstNumbersInListThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits(
                        numberList,
                        testCase.maxNumberToEvaluate,
                        testCase.numbersToTake,
                        testCase.maxNumberLength
                    )

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


    fun errorInSequenceOperation(e: Throwable) {
        _bigCollectionsViewModelLiveData.postValue(SequenceOperationError(e.toString()))
    }

    fun findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
        numberList: Sequence<Int>,
        testCase: BigCollectionsTestCase,
        testCaseNumber: Int
    ) {
        _bigCollectionsViewModelLiveData.postValue(PerformingOperation)

        if (testCaseNumber < defaultTestCases.size - 1) {
            AsyncTask.execute {
                val result =
                    FindFirstNumbersInSequenceThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits(
                        numberList,
                        testCase.maxNumberToEvaluate,
                        testCase.numbersToTake,
                        testCase.maxNumberLength
                    )

                _bigCollectionsViewModelLiveData.postValue(
                    ExecuteNextSequenceTestCase(
                        BigCollectionsTestCaseResult(
                            result.time,
                            result.elements
                        )
                    )
                )
            }
        } else {
            AsyncTask.execute {
                val result =
                    FindFirstNumbersInSequenceThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits(
                        numberList,
                        testCase.maxNumberToEvaluate,
                        testCase.numbersToTake,
                        testCase.maxNumberLength
                    )

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

    fun errorInListOperation(e: Throwable) {
        _bigCollectionsViewModelLiveData.postValue(ListOperationError(e.toString()))
    }

}