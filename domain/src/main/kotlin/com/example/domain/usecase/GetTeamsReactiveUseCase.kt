package com.example.domain.usecase

import com.example.domain.repository.TeamsRepository

/**
 * Use case that retrieves teams of the database by reactive way
 * @param teamsRepository repository to connect with the next layer and be able to perform the operations
 */
class GetTeamsReactiveUseCase(private val teamsRepository: TeamsRepository) {

    /**
     * Executes Use case
     * @return data flow with the teams retrieved
     */
    operator fun invoke() = teamsRepository.getTeamsReactive()
}