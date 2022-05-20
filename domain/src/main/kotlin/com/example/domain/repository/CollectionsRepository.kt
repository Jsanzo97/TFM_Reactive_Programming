package com.example.domain.repository

import com.example.domain.entity.CollectionsResult

/**
 * Definition of the functions in the Domain layer that we need to implement in the next layer
 */
interface CollectionsRepository {

    /**
     * Communicate the Domain layer with the next layer to execute the list operations
     * @param numberList list of numbers to execute the test
     * @param maxNumbersToEvaluate number of elements that will be taken for test
     * @param numbersToTake number of odd elements that will be taken to check if they are primes
     * @param maxNumberLength max length of numbers permitted
     */
    fun findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigits(
        numberList: List<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): CollectionsResult

    /**
     * Communicate the Domain layer with the next layer to execute the sequence operations
     * @param numberSequence sequence of numbers to execute the test
     * @param maxNumbersToEvaluate number of elements that will be taken for test
     * @param numbersToTake number of odd elements that will be taken to check if they are primes
     * @param maxNumberLength max length of numbers permitted
     */
    fun findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
        numberSequence: Sequence<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): CollectionsResult

}