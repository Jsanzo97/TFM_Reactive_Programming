package com.example.domain.entity

/**
 * Represent sport in the system
 * @param name name of the sport
 * @param numOfPlayers number of players in the sport
 */
data class Sport(
    val name: String,
    val numOfPlayers: Int
)

// Predefined sports
val listOfPredefinedSports = listOf(
    Sport("Football", 11),
    Sport("Basketball", 5),
    Sport("Volley ball", 7)
)

/**
 * Obtains random sport from the list
 * @return random sport from list
 */
fun takeRandomSport() = listOfPredefinedSports[(listOfPredefinedSports.indices).random()]
