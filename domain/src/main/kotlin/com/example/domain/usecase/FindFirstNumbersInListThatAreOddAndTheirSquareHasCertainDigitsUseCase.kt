package com.example.domain.usecase

import com.example.domain.repository.CollectionsRepository

class FindFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigitsUseCase(
    private val collectionsRepository: CollectionsRepository
) {
    operator fun invoke(
        numberList: List<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ) = collectionsRepository.findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigits(
        numberList, maxNumbersToEvaluate, numbersToTake, maxNumberLength
    )
}