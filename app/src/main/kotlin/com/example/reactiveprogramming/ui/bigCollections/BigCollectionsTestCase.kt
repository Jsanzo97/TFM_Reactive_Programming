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

val defaultTestCases = listOf(
    BigCollectionsTestCase(
        0,
        100,
        50,
        10,
        2
    ),
    BigCollectionsTestCase(
        1,
        1000,
        500,
        100,
        3
    ),
    BigCollectionsTestCase(
        2,
        10000,
        5000,
        1000,
        4
    ),
    BigCollectionsTestCase(
        3,
        100000,
        5000,
        1000,
        5
    ),
    BigCollectionsTestCase(
        4,
        1000000,
        50000,
        1000,
        6
    ),
    BigCollectionsTestCase(
        5,
        10000000,
        500000,
        10000,
        7
    ),
    BigCollectionsTestCase(
        -1,
        100000000,
        5000000,
        100000,
        8
    )
)