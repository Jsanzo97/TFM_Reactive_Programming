package com.example.reactiveprogramming.di.dataManagement

import com.example.domain.usecase.*
import com.example.reactiveprogramming.ui.dataManagement.DataManagementViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Used to create the necessary use cases to create the DataManagementViewModel and make it available
 * to be injected into DataManagementFragment
 */
val dataManagementModule = module {

    viewModel { DataManagementViewModel(get(), get(), get(), get(), get(), get()) }

    factory { InitializeDatabaseUseCase(get()) }
    factory { GetTeamsReactiveUseCase(get()) }
    factory { GetTeamsFunctionalUseCase(get()) }
    factory { UpdateTeamUseCase(get()) }
    factory { RemoveTeamUseCase(get()) }
    factory { CreateTeamUseCase(get()) }

}