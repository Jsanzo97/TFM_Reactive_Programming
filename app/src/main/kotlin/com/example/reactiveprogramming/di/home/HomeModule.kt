package com.example.reactiveprogramming.di.home

import com.example.common.UiConfigurationViewModel
import com.example.domain.usecase.InitializeDatabaseUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    viewModel { UiConfigurationViewModel() }

}