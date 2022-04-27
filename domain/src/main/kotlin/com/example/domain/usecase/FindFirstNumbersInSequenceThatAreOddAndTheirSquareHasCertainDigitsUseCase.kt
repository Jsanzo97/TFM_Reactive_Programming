package com.example.domain.usecase

import com.example.domain.repository.CollectionsRepository

class FindFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigitsUseCase(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(
        numberList: Sequence<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ) = collectionsRepository.findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
        numberList, maxNumbersToEvaluate, numbersToTake, maxNumberLength
    )
}