package com.example.collections

import com.example.data.entity.DataCollectionResult

data class CollectionsManagerResult (
    val elements: List<Int>,
    val time: Double
)

fun CollectionsManagerResult.toDataCollectionResult() = DataCollectionResult(
    elements, time
)