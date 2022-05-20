package com.example.data.entity

import com.example.domain.entity.Team

/**
 * Represent team in the system
 * @param teamId id of the team
 * @param name name of the team
 * @param sport sport that the team plays
 * @param players players of the team
 */
data class DataTeam (
    val teamId: Long,
    val name: String,
    val sport: DataSport,
    val players: List<DataPlayer>
)

/**
 * Convert Team to DataTeam
 * @return new DataTeam with the Team values
 */
fun Team.toDataTeam() = DataTeam(
    teamId, name, sport.toDataSport(), players.map { it.toDataPlayer() }
)

/**
 * Convert DataTeam to Team
 * @return new Team with the DataTeam values
 */
fun DataTeam.toTeam() = Team(
    teamId, name, sport.toSport(), players.map { it.toPlayer() }
)