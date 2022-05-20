package com.example.collections

/**
 * Manage the operations performed on lists and sequences to evaluate their differences
 */
class CollectionsManager {

    /**
     * Given number list of N elements,
     * Take the M first odd numbers,
     * Then take only primes,
     * Then calculate the square of each one,
     * Then remove those that has more than D digits,
     * Finally sort the result descending
     *
     * @param numberList list of numbers to execute the operations
     * @param maxNumbersToEvaluate number of elements that will be taken to check if they are odd
     * @param numbersToTake number of odd elements that will be taken to check if they are primes
     * @param maxNumberLength max length of numbers permitted
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

    /**
     * Given number sequence of N elements,
     * Take the M first odd numbers,
     * Then take only primes,
     * Then calculate the square of each one,
     * Then remove those that has more than D digits,
     * Finally sort the result descending
     *
     * @param numberSequence sequence of numbers to execute the operations
     * @param maxNumbersToEvaluate number of elements that will be taken to check if they are odd
     * @param numbersToTake number of odd elements that will be taken to check if they are primes
     * @param maxNumberLength max length of numbers permitted
     */
    fun findFirstNumbersInSequenceThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits(
        numberSequence: Sequence<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): CollectionsManagerResult {
        val startTime = System.currentTimeMillis()
        val elements = numberSequence
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

    /**
     * Check if int number is prime or not
     * @return true if the number is prime, false if not
     */
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