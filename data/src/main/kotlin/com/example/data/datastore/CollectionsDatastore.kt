package com.example.data.datastore

import com.example.data.entity.DataCollectionResult

/**
 * Definition of the functions in the Data layer that we need to implement in the next layer
 */
interface CollectionsDatastore {

    /**
     * Communicate the Data layer with the next layer to execute the list operations
     * @param numberList list of numbers to execute the test
     * @param maxNumbersToEvaluate number of elements that will be taken for test
     * @param numbersToTake number of odd elements that will be taken to check if they are primes
     * @param maxNumberLength max length of numbers permitted
     * @return data with the elements filtered and the necessary time
     */
    fun findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigits(
        numberList: List<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): DataCollectionResult

    /**
     * Communicate the Data layer with the next layer to execute the sequence operations
     * @param numberSequence sequence of numbers to execute the test
     * @param maxNumbersToEvaluate number of elements that will be taken for test
     * @param numbersToTake number of odd elements that will be taken to check if they are primes
     * @param maxNumberLength max length of numbers permitted
     * @return data with the elements filtered and the necessary time
     */
    fun findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
        numberSequence: Sequence<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): DataCollectionResult

}