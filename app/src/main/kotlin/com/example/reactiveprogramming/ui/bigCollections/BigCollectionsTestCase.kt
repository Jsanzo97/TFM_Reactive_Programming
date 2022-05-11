package com.example.reactiveprogramming.ui.bigCollections

data class BigCollectionsTestCase(
    val listLength: Int,
    val maxNumberToEvaluate: Int,
    val numbersToTake: Int,
    val maxNumberLength: Int
)

data class BigCollectionsTestCaseResult(
    val time: Double,
    val elements: List<Int>
)

const val THOUSAND = 1_000
const val TWO_THOUSAND = 2_000
const val THREE_THOUSAND = 3_000
const val FOUR_THOUSAND = 4_000
const val FIVE_THOUSAND = 5_000

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