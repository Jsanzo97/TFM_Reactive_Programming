package com.example.reactiveprogramming.ui.bigCollections

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
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ) {
        _bigCollectionsViewModelLiveData.postValue(PerformingOperation)

        val startTimeOperation = System.currentTimeMillis()

        viewModelScope.launch {
            _bigCollectionsViewModelLiveData.value = ListOperationFinished(
                findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigitsUseCase(
                    numberList, maxNumbersToEvaluate, numbersToTake, maxNumberLength
                ),
                (System.currentTimeMillis() - startTimeOperation) / 1000.0
            )
        }
    }


    fun findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
        numberList: Sequence<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ) {
        _bigCollectionsViewModelLiveData.postValue(PerformingOperation)

        val startTimeOperation = System.currentTimeMillis()

        viewModelScope.launch {
            _bigCollectionsViewModelLiveData.value = SequenceOperationFinished(
                findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigitsUseCase(
                    numberList, maxNumbersToEvaluate, numbersToTake, maxNumberLength
                ),
                (System.currentTimeMillis() - startTimeOperation) / 1000.0
            )
        }
    }

}