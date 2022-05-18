package com.example.reactiveprogramming.di.collections

import com.example.domain.usecase.FindFirstNumbersInListThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits
import com.example.domain.usecase.FindFirstNumbersInSequenceThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits
import com.example.reactiveprogramming.ui.bigCollections.BigCollectionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Used to create the necessary use cases to create the BigCollectionsViewModel and make it available
 * to be injected into BigCollectionsFragment
 */
val collectionsModule = module {

    viewModel { BigCollectionsViewModel(get(), get()) }

    factory { FindFirstNumbersInListThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits(get()) }
    factory { FindFirstNumbersInSequenceThatAreOddAndSelectFirstPrimesAndTheirSquareHasCertainDigits(get()) }

}