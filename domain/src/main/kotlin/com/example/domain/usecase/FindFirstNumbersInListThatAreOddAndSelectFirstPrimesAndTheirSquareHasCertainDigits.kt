package com.example.domain.usecase

import com.example.domain.repository.CollectionsRepository

/**
 * Use case that execute some operations on list and return the final elements and the necessary time to do it
 * @param collectionsRepository repository to connect with the next layer and be able to perform the operations
 */
class FindFirstNumbersInListThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits(
    private val collectionsRepository: CollectionsRepository
) {

    /**
     * Execute the Use Case
     * @param numberList list of numbers to execute the test
     * @param maxNumbersToEvaluate number of elements that will be taken for test
     * @param numbersToTake number of odd elements that will be taken to check if they are primes
     * @param maxNumberLength max length of numbers permitted
     */
    operator fun invoke(
        numberList: List<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ) = collectionsRepository.findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigits(
        numberList, maxNumbersToEvaluate, numbersToTake, maxNumberLength
    )
}