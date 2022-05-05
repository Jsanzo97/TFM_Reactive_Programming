package com.example.data.entity

import com.example.domain.entity.CollectionsResult

data class DataCollectionResult(
    val elements: List<Int>,
    val time: Double
)

fun DataCollectionResult.toCollectionResult() = CollectionsResult(
    elements, time
)