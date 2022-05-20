package com.example.domain.entity

/**
 * Represent player in the system
 * @param name name of the player
 * @param age age of the player
 */
data class Player(
    val name: String,
    val age: Int
)

/**
 * Generates random player
 * @param number number of the player to differentiate it from others
 * @return new player with random data
 */
fun generateRandomPlayer(number: Int) = Player(
    "Player $number",
    (18..65).random()
)