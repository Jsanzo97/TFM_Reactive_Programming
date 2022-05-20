package com.example.domain.usecase

import arrow.core.None
import arrow.core.Option
import arrow.core.some
import com.example.domain.entity.Team
import com.example.domain.error.CustomError
import com.example.domain.repository.TeamsRepository

/**
 * Use case that cleans the database and save the teams to make a clean initialization
 * @param teamsRepository repository to connect with the next layer and be able to perform the operations
 */
class InitializeDatabaseUseCase(private val teamsRepository: TeamsRepository) {

    /**
     * Executes Use case
     * @param team list of teams to initialize the database
     * @return None if the operations are success, CustomError object if not
     */
    suspend operator fun invoke(team: List<Team>): Option<CustomError> =
        teamsRepository.clearDatabase().fold({
            teamsRepository.saveTeam(team).fold({
                    None
                },
                {
                    it.some()
                }
            )
        },
        {
            it.some()
        }
    )
}