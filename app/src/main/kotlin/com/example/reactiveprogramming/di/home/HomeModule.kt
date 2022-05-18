package com.example.reactiveprogramming.di.home

import com.example.common.UiConfigurationViewModel
import com.example.domain.usecase.InitializeDatabaseUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * This module is used to create the necessary use cases to create the UiConfigurationViewModel and make it available
 * to be injected in all the fragments
 */
val homeModule = module {

    viewModel { UiConfigurationViewModel() }

}