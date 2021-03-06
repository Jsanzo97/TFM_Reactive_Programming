package com.example.reactiveprogramming.di.home

import com.example.common.UiConfigurationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Used to create the necessary use cases to create the UiConfigurationViewModel and make it available
 * to be injected in all the fragments
 */
val homeModule = module {

    viewModel { UiConfigurationViewModel() }

}