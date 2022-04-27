package com.example.reactiveprogramming.di.collections

import com.example.domain.usecase.FindFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigitsUseCase
import com.example.domain.usecase.FindFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigitsUseCase
import com.example.reactiveprogramming.ui.bigCollections.BigCollectionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val collectionsModule = module {

    viewModel { BigCollectionsViewModel(get(), get()) }

    factory { FindFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigitsUseCase(get()) }
    factory { FindFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigitsUseCase(get()) }

}