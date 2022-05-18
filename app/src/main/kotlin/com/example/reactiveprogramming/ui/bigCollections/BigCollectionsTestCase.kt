package com.example.reactiveprogramming.ui.bigCollections

/**
 * Test case that will be executed with Sequences and Lists
 *
 * The operations executed for each test will be:
 *  1. Take N numbers from the collection (maxNumberToEvaluate)
 *  2. Take only odd numbers
 *  3. Take M numbers (numbersToTake)
 *  4. Take only prime numbers
 *  5. Calculate the square of each number
 *  6. Convert numbers to String
 *  7. Take only numbers which its length is less than L (maxNumberLength)
 *  8. Convert numbers to Int
 *  9. Sort numbers descending
 *
 * @param collectionLength length of the collection that will be created
 * @param maxNumberToEvaluate number of elements that will be taken for test
 * @param numbersToTake number of odd elements that will be taken to check if they are primes
 * @param maxNumberLength max length of numbers permitted
 */
data class BigCollectionsTestCase(
    val collectionLength: Int,
    val maxNumberToEvaluate: Int,
    val numbersToTake: Int,
    val maxNumberLength: Int
)

/**
 * Result of execute a test case
 * @param time necessary time to perform the test
 * @param elements elements that pass the test
 */
data class BigCollectionsTestCaseResult(
    val time: Double,
    val elements: List<Int>
)

//Constants to create the tests
const val THOUSAND = 1_000
const val TEN_THOUSAND = 10_000
const val FIFTY_THOUSAND = 50_000
const val SIXTY_THOUSAND = 50_000
const val SEVENTY_THOUSAND = 50_000
const val EIGHTY_THOUSAND = 50_000
const val NINETY_THOUSAND = 50_000
const val ONE_HUNDRED_THOUSAND = 100_000
const val TWO_HUNDRED_THOUSAND = 200_000
const val THREE_HUNDRED_THOUSAND = 300_000
const val FOUR_HUNDRED_THOUSAND = 400_000
const val FIVE_HUNDRED_THOUSAND = 500_000
const val ONE_MILLION = 1_000_000

//List of test cases
val defaultTestCases = listOf(

    // FIFTY THOUSAND TEST
    BigCollectionsTestCase(
        ONE_MILLION,
        FIFTY_THOUSAND,
        THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        FIFTY_THOUSAND,
        THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        FIFTY_THOUSAND,
        THOUSAND,
        9
    ),

    // SIXTY THOUSAND TEST
    BigCollectionsTestCase(
        ONE_MILLION,
        SIXTY_THOUSAND,
        THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        SIXTY_THOUSAND,
        THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        SIXTY_THOUSAND,
        THOUSAND,
        9
    ),

    // SEVENTY THOUSAND TEST
    BigCollectionsTestCase(
        ONE_MILLION,
        SEVENTY_THOUSAND,
        THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        SEVENTY_THOUSAND,
        THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        SEVENTY_THOUSAND,
        THOUSAND,
        9
    ),

    // EIGHTY THOUSAND TEST
    BigCollectionsTestCase(
        ONE_MILLION,
        EIGHTY_THOUSAND,
        THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        EIGHTY_THOUSAND,
        THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        EIGHTY_THOUSAND,
        THOUSAND,
        9
    ),

    // NINETY THOUSAND TEST
    BigCollectionsTestCase(
        ONE_MILLION,
        NINETY_THOUSAND,
        THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        NINETY_THOUSAND,
        THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        NINETY_THOUSAND,
        THOUSAND,
        9
    ),

    // ONE HUNDRED THOUSAND TEST
    BigCollectionsTestCase(
        ONE_MILLION,
        ONE_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        ONE_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        ONE_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        9
    ),

    // TWO HUNDRED THOUSAND TEST
    BigCollectionsTestCase(
        ONE_MILLION,
        TWO_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        TWO_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        TWO_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        9
    ),

    // THREE HUNDRED THOUSAND TEST
    BigCollectionsTestCase(
        ONE_MILLION,
        THREE_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        THREE_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        THREE_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        9
    ),

    // FOUR HUNDRED THOUSAND TEST
    BigCollectionsTestCase(
        ONE_MILLION,
        FOUR_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        FOUR_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        FOUR_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        9
    ),

    // FIVE HUNDRED THOUSAND TEST
    BigCollectionsTestCase(
        ONE_MILLION,
        FIVE_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        FIVE_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        ONE_MILLION,
        FIVE_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        9
    )
)