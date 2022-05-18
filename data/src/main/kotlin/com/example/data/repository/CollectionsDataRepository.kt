package com.example.data.repository

import com.example.data.datastore.CollectionsDatastore
import com.example.data.entity.toCollectionResult
import com.example.domain.entity.CollectionsResult
import com.example.domain.repository.CollectionsRepository

class CollectionsDataRepository (
    private val collectionsDatastore: CollectionsDatastore
) : CollectionsRepository {

    override fun findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigits(
        numberList: List<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): CollectionsResult =
        collectionsDatastore.findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigits(
            numberList, maxNumbersToEvaluate, numbersToTake, maxNumberLength
        ).toCollectionResult()

    override fun findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
        numberList: Sequence<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): CollectionsResult =
        collectionsDatastore.findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
            numberList, maxNumbersToEvaluate, numbersToTake, maxNumberLength
        ).toCollectionResult()

}