package com.example.domain.usecase

import com.example.domain.repository.TeamsRepository

/**
 * Use case that retrieves teams of the database by functional way
 * @param teamsRepository repository to connect with the next layer and be able to perform the operations
 */
class GetTeamsFunctionalUseCase(private val teamsRepository: TeamsRepository) {

    /**
     * Executes Use case
     * @return list of teams retrieved
     */
    operator fun invoke() = teamsRepository.getTeamsFunctional()
}