package com.example.data.repository

import com.example.data.datastore.CollectionsDatastore
import com.example.domain.repository.CollectionsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CollectionsDataRepository (
    private val collectionsDatastore: CollectionsDatastore
) : CollectionsRepository {

    override fun findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigits(
        numberList: List<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): List<Int> =
        collectionsDatastore.findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigits(
            numberList, maxNumbersToEvaluate, numbersToTake, maxNumberLength
        )


    override fun findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
        numberList: Sequence<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): List<Int> =
        collectionsDatastore.findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
            numberList, maxNumbersToEvaluate, numbersToTake, maxNumberLength
        )


}