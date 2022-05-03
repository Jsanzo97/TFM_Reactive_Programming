package com.example.reactiveprogramming.ui.bigCollections

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.FindFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigitsUseCase
import com.example.domain.usecase.FindFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigitsUseCase
import kotlinx.coroutines.launch

class BigCollectionsViewModel(
    private val findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigitsUseCase:
        FindFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigitsUseCase,
    private val findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigitsUseCase:
        FindFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigitsUseCase
): ViewModel() {

    private val _bigCollectionsViewModelLiveData = MutableLiveData<BigCollectionsViewState>()
    val bigCollectionsViewModelLiveData: LiveData<BigCollectionsViewState> get() = _bigCollectionsViewModelLiveData

    fun findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigits(
        numberList: List<Int>,
        testCase: BigCollectionsTestCase
    ) {
        _bigCollectionsViewModelLiveData.postValue(PerformingOperation)

        AsyncTask.execute {
            val startTimeOperation = System.currentTimeMillis()

            if (testCase.id != -1) {
                val result = findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigitsUseCase(
                    numberList,
                    testCase.maxNumberToEvaluate,
                    testCase.numbersToTake,
                    testCase.maxNumberLength
                )

                val time = (System.currentTimeMillis() - startTimeOperation) / 1000.0

                _bigCollectionsViewModelLiveData.postValue(
                    ExecuteNextListTestCase(
                        BigCollectionsTestCaseResult(
                            testCase.id,
                            time,
                            result
                        )
                    )
                )
            } else {
                val result = findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigitsUseCase(
                    numberList,
                    testCase.maxNumberToEvaluate,
                    testCase.numbersToTake,
                    testCase.maxNumberLength
                )

                val time = (System.currentTimeMillis() - startTimeOperation) / 1000.0

                _bigCollectionsViewModelLiveData.postValue(
                    ListTestCasesFinished(
                        BigCollectionsTestCaseResult(
                            testCase.id,
                            time,
                            result
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
        testCase: BigCollectionsTestCase
    ) {
        _bigCollectionsViewModelLiveData.postValue(PerformingOperation)

        AsyncTask.execute {
            val startTimeOperation = System.currentTimeMillis()

            if (testCase.id != -1) {
                val result =
                    findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigitsUseCase(
                        numberList,
                        testCase.maxNumberToEvaluate,
                        testCase.numbersToTake,
                        testCase.maxNumberLength
                    )

                val time = (System.currentTimeMillis() - startTimeOperation) / 1000.0

                _bigCollectionsViewModelLiveData.postValue(
                    ExecuteNextSequenceTestCase(
                        BigCollectionsTestCaseResult(
                            testCase.id,
                            time,
                            result
                        )
                    )
                )
            } else {
                val result =
                    findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigitsUseCase(
                        numberList,
                        testCase.maxNumberToEvaluate,
                        testCase.numbersToTake,
                        testCase.maxNumberLength
                    )

                val time = (System.currentTimeMillis() - startTimeOperation) / 1000.0

                _bigCollectionsViewModelLiveData.postValue(
                    SequenceTestCasesFinished(
                        BigCollectionsTestCaseResult(
                            testCase.id,
                            time,
                            result
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