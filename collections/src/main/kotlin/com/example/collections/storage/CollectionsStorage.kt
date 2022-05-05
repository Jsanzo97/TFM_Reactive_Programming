package com.example.collections.storage

import com.example.collections.CollectionsManager
import com.example.collections.CollectionsManagerResult
import com.example.collections.toDataCollectionResult
import com.example.data.datastore.CollectionsDatastore
import com.example.data.entity.DataCollectionResult

class CollectionsStorage: CollectionsDatastore {

    override fun findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigits(
        numberList: List<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): DataCollectionResult = CollectionsManager().findFirstNumbersInListThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits(
        numberList, maxNumbersToEvaluate, numbersToTake, maxNumberLength
    ).toDataCollectionResult()

    override fun findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
        numberList: Sequence<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): DataCollectionResult = CollectionsManager().findFirstNumbersInSequenceThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits(
        numberList, maxNumbersToEvaluate, numbersToTake, maxNumberLength
    ).toDataCollectionResult()

}