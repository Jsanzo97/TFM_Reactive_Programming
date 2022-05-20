package com.example.domain.usecase

import arrow.core.None
import arrow.core.Option
import arrow.core.some
import com.example.domain.entity.Team
import com.example.domain.error.CustomError
import com.example.domain.repository.TeamsRepository

/**
 * Use case that updates a team in the database
 * @param teamsRepository repository to connect with the next layer and be able to perform the operations
 */
class UpdateTeamUseCase(private val teamsRepository: TeamsRepository) {

    /**
     * Executes Use case
     * @param team team that will be updated
     * @return None if the operation is success, CustomError object if not
     */
    suspend operator fun invoke(team: Team): Option<CustomError> =
        teamsRepository.updateTeam(team).fold({
            None
        },
        {
            it.some()
        }
    )
}