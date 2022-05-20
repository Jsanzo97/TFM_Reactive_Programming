package com.example.domain.entity

/**
 * Represent team in the system
 * @param teamId id of the team
 * @param name name of the team
 * @param sport sport that the team plays
 * @param players players of the team
 */
data class Team (
    val teamId: Long,
    val name: String,
    val sport: Sport,
    val players: List<Player>
)

/**
 * Generates team with random data
 * @param number number of the team to differentiate it from others
 * @return new team with random data
 */
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