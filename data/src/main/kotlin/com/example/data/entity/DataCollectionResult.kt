package com.example.data.entity

import com.example.domain.entity.CollectionsResult

/**
 * Represent the result of perform operations in collections
 * @param elements elements filetered
 * @param time necessary time to do the operations
 */
data class DataCollectionResult(
    val elements: List<Int>,
    val time: Double
)

/**
 * Convert DataCollectionResult to CollectionResult
 * @return new CollectionResult with the DataCollectionResult values
 */
fun DataCollectionResult.toCollectionResult() = CollectionsResult(
    elements, time
)