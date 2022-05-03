package com.example.data.datastore

interface CollectionsDatastore {

    fun findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigits(
        numberList: List<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): List<Int>

    fun findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
        numberList: Sequence<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): List<Int>

}