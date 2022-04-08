package com.example.domain.usecase

import arrow.core.None
import arrow.core.Option
import arrow.core.some
import com.example.domain.entity.Team
import com.example.domain.error.CustomError
import com.example.domain.repository.TeamsRepository

class CreateTeamUseCase(private val teamsRepository: TeamsRepository) {
    suspend operator fun invoke(team: Team): Option<CustomError> =
        teamsRepository.saveTeam(team).fold({
            None
        },
            {
                it.some()
            }
        )
}