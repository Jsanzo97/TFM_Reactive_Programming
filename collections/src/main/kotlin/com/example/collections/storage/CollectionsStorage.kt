package com.example.collections.storage

import com.example.collections.CollectionsManager
import com.example.data.datastore.CollectionsDatastore

class CollectionsStorage: CollectionsDatastore {

    override suspend fun findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigits(
        numberList: List<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): List<Int> = CollectionsManager().findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigits(
        numberList, maxNumbersToEvaluate, numbersToTake, maxNumberLength
    )

    override suspend fun findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
        numberList: Sequence<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): List<Int> = CollectionsManager().findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
        numberList, maxNumbersToEvaluate, numbersToTake, maxNumberLength
    )

}