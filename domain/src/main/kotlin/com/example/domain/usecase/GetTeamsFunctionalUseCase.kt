package com.example.domain.usecase

import com.example.domain.repository.TeamsRepository

class GetTeamsFunctionalUseCase(private val teamsRepository: TeamsRepository) {
    operator fun invoke() = teamsRepository.getTeamsFunctional()
}