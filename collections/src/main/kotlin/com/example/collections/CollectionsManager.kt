package com.example.collections

class CollectionsManager {

    /* Given number list of N elements, take the M first odd numbers, calculate the square of each one, take only prime
       numbers and remove those that has more than D digits.
     */
    fun findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigits(
        numberList: List<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): List<Int> {
        return numberList
            .takeWhile { it < maxNumbersToEvaluate }
            .filter { it % 2 != 0 }
            .take(numbersToTake)
            .filter { it.isPrime() }
            .map { it * it }
            .map { it.toString() }
            .filter { it.length < maxNumberLength }
            .map { it.toInt() }
    }

    /* Given number sequence of N elements, take the M first odd numbers, calculate the square of each one, take only prime
       numbers and remove those that has more than D digits.
     */
    fun findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
        numberList: Sequence<Int>,
        maxNumbersToEvaluate: Int,
        numbersToTake: Int,
        maxNumberLength: Int
    ): List<Int> {
        return numberList
            .takeWhile { it < maxNumbersToEvaluate }
            .filter { it % 2 != 0 }
            .take(numbersToTake)
            .filter { it.isPrime() }
            .map { it * it }
            .map { it.toString() }
            .filter { it.length < maxNumberLength }
            .map { it.toInt() }
            .toList()
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