package com.example.collections

class CollectionsManager {

    /* Given number list of N elements,
       Take the M first odd numbers,
       Then take only primes,
       Then calculate the square of each one,
       And finally remove those that has more than D digits.
     */
    fun findFirstNumbersInListThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits(
        numberList: List<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): CollectionsManagerResult {
        val startTime = System.currentTimeMillis()
        val elements = numberList
            .takeWhile { it < maxNumbersToEvaluate }
            .filter { it % 2 != 0 }
            .take(numbersToTake)
            .filter { it.isPrime() }
            .map { it * it }
            .map { it.toString() }
            .filter { it.length < maxNumberLength }
            .map { it.toInt() }
            .sortedDescending()
        val endTime = System.currentTimeMillis()
        return CollectionsManagerResult(
            elements,
            (endTime - startTime) / 1000.0
        )
    }

    /* Given number sequence of N elements,
       Take the M first odd numbers,
       Then take only primes,
       Then calculate the square of each one,
       And finally remove those that has more than D digits.
     */
    fun findFirstNumbersInSequenceThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits(
        numberList: Sequence<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): CollectionsManagerResult {
        val startTime = System.currentTimeMillis()
        val elements = numberList
            .takeWhile { it < maxNumbersToEvaluate }
            .filter { it % 2 != 0 }
            .take(numbersToTake)
            .filter { it.isPrime() }
            .map { it * it }
            .map { it.toString() }
            .filter { it.length < maxNumberLength }
            .map { it.toInt() }
            .sortedDescending()
            .toList()
        val endTime = System.currentTimeMillis()
        return CollectionsManagerResult(
            elements,
            (endTime - startTime) / 1000.0
        )
    }

    private fun Int.isPrime(): Boolean {
        if (this <= 3) {
            return true
        }  else {
            for (i in 2..this / 2) {
                if (this % i == 0) {
                    return false
                }
            }
            return true
        }
    }
}