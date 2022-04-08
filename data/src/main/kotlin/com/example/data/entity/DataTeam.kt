package com.example.data.entity

import com.example.domain.entity.Team

data class DataTeam (
    val teamId: Long,
    val name: String,
    val sport: DataSport,
    val players: List<DataPlayer>
)

fun Team.toDataTeam() = DataTeam(
    teamId, name, sport.toDataSport(), players.map { it.toDataPlayer() }
)

fun DataTeam.toTeam() = Team(
    teamId, name, sport.toSport(), players.map { it.toPlayer() }
)