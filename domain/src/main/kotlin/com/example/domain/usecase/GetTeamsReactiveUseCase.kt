package com.example.domain.usecase

import com.example.domain.repository.TeamsRepository

class GetTeamsReactiveUseCase(private val teamsRepository: TeamsRepository) {
    operator fun invoke() = teamsRepository.getTeamsReactive()
}