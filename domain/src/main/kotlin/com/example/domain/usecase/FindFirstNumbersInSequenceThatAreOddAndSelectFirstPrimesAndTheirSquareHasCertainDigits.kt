package com.example.domain.usecase

import com.example.domain.repository.CollectionsRepository

/**
 * Use case that executes some operations on sequence and return the final elements and the necessary time to do it
 * @param collectionsRepository repository to connect with the next layer and be able to perform the operations
 */
class FindFirstNumbersInSequenceThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits(
    private val collectionsRepository: CollectionsRepository
) {

    /**
     * Executes Use Case
     * @param numberSequence sequence of numbers to execute the test
     * @param maxNumbersToEvaluate number of elements that will be taken for test
     * @param numbersToTake number of odd elements that will be taken to check if they are primes
     * @param maxNumberLength max length of numbers permitted
     * @return result with the elements filtered and the necessary time to do it
     */
    operator fun invoke(
        numberSequence: Sequence<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ) = collectionsRepository.findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
        numberSequence, maxNumbersToEvaluate, numbersToTake, maxNumberLength
    )
}