package com.example.reactiveprogramming.ui.bigCollections

data class BigCollectionsTestCase(
    val id: Int,
    val listLength: Int,
    val maxNumberToEvaluate: Int,
    val numbersToTake: Int,
    val maxNumberLength: Int
)

data class BigCollectionsTestCaseResult(
    val id: Int,
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
        0,
        ONE_MILLION,
        FIFTY_THOUSAND,
        THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        1,
        ONE_MILLION,
        FIFTY_THOUSAND,
        THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        2,
        ONE_MILLION,
        FIFTY_THOUSAND,
        THOUSAND,
        9
    ),

    // SIXTY THOUSAND TEST
    BigCollectionsTestCase(
        3,
        ONE_MILLION,
        SIXTY_THOUSAND,
        THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        4,
        ONE_MILLION,
        SIXTY_THOUSAND,
        THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        5,
        ONE_MILLION,
        SIXTY_THOUSAND,
        THOUSAND,
        9
    ),

    // SEVENTY THOUSAND TEST
    BigCollectionsTestCase(
        6,
        ONE_MILLION,
        SEVENTY_THOUSAND,
        THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        7,
        ONE_MILLION,
        SEVENTY_THOUSAND,
        THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        8,
        ONE_MILLION,
        SEVENTY_THOUSAND,
        THOUSAND,
        9
    ),

    // EIGHTY THOUSAND TEST
    BigCollectionsTestCase(
        9,
        ONE_MILLION,
        EIGHTY_THOUSAND,
        THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        10,
        ONE_MILLION,
        EIGHTY_THOUSAND,
        THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        11,
        ONE_MILLION,
        EIGHTY_THOUSAND,
        THOUSAND,
        9
    ),

    // NINETY THOUSAND TEST
    BigCollectionsTestCase(
        12,
        ONE_MILLION,
        NINETY_THOUSAND,
        THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        13,
        ONE_MILLION,
        NINETY_THOUSAND,
        THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        14,
        ONE_MILLION,
        NINETY_THOUSAND,
        THOUSAND,
        9
    ),

    // ONE HUNDRED THOUSAND TEST
    BigCollectionsTestCase(
        15,
        ONE_MILLION,
        ONE_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        16,
        ONE_MILLION,
        ONE_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        17,
        ONE_MILLION,
        ONE_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        9
    ),

    // TWO HUNDRED THOUSAND TEST
    BigCollectionsTestCase(
        18,
        ONE_MILLION,
        TWO_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        19,
        ONE_MILLION,
        TWO_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        20,
        ONE_MILLION,
        TWO_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        9
    ),

    // THREE HUNDRED THOUSAND TEST
    BigCollectionsTestCase(
        21,
        ONE_MILLION,
        THREE_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        22,
        ONE_MILLION,
        THREE_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        23,
        ONE_MILLION,
        THREE_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        9
    ),

    // FOUR HUNDRED THOUSAND TEST
    BigCollectionsTestCase(
        24,
        ONE_MILLION,
        FOUR_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        25,
        ONE_MILLION,
        FOUR_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        26,
        ONE_MILLION,
        FOUR_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        9
    ),

    // FIVE HUNDRED THOUSAND TEST
    BigCollectionsTestCase(
        27,
        ONE_MILLION,
        FIVE_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        7
    ),
    BigCollectionsTestCase(
        28,
        ONE_MILLION,
        FIVE_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        8
    ),
    BigCollectionsTestCase(
        -1,
        ONE_MILLION,
        FIVE_HUNDRED_THOUSAND,
        TEN_THOUSAND,
        9
    )
)