package com.example.collections.storage

import com.example.collections.CollectionsManager
import com.example.collections.toDataCollectionResult
import com.example.data.datastore.CollectionsDatastore
import com.example.data.entity.DataCollectionResult

/**
 * Implementation of CollectionsDatastore to communicate the Data layer with the Collections layer
 * @see CollectionsDatastore
 */
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
        numberSequence: Sequence<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): DataCollectionResult = CollectionsManager().findFirstNumbersInSequenceThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits(
        numberSequence, maxNumbersToEvaluate, numbersToTake, maxNumberLength
    ).toDataCollectionResult()

}