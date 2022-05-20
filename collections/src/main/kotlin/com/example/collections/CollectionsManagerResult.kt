package com.example.collections

import com.example.data.entity.DataCollectionResult

/**
 * Result of perform some operations on list or sequence
 * @param elements elements that pass all filters
 * @param time necessary time to perform all operations
 */
data class CollectionsManagerResult (
    val elements: List<Int>,
    val time: Double
)

/**
 * Convert CollectionsManagerResult to DataCollectionResult
 * @return new DataCollectionResult with the CollectionsManagerResult values
 */
fun CollectionsManagerResult.toDataCollectionResult() = DataCollectionResult(
    elements, time
)