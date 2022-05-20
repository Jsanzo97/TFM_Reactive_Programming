package com.example.data.repository

import com.example.data.datastore.CollectionsDatastore
import com.example.data.entity.toCollectionResult
import com.example.domain.entity.CollectionsResult
import com.example.domain.repository.CollectionsRepository

/**
 * Implementation of CollectionsRepository to communicate the Domain layer with the Data layer
 * @see CollectionsRepository
 */
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
        numberSequence: Sequence<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): CollectionsResult =
        collectionsDatastore.findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
            numberSequence, maxNumbersToEvaluate, numbersToTake, maxNumberLength
        ).toCollectionResult()

}