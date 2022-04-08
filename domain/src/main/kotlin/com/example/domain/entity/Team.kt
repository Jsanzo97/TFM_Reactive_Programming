package com.example.domain.entity

data class Team (
    val teamId: Long,
    val name: String,
    val sport: Sport,
    val players: List<Player>
)

fun generateRandomTeam(number: Int? = null): Team {
    val sport = takeRandomSport()
    val players = (0 until sport.numOfPlayers).map { generateRandomPlayer(it) }

    return Team(
        System.nanoTime(),
        if (number != null) { "Team $number"} else {"New Team"},
        sport,
        players
    )
}