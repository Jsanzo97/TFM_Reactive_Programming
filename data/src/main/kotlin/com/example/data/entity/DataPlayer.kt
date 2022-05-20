package com.example.data.entity

import com.example.domain.entity.Player

/**
 * Represent player in the system
 * @param name name of the player
 * @param age age of the player
 */
data class DataPlayer (
    val name: String,
    val age: Int
)

/**
 * Convert Player to DataPlayer
 * @return new DataPlayer with the Player values
 */
fun Player.toDataPlayer() = DataPlayer(
    name, age
)

/**
 * Convert DataPlayer to Player
 * @return new Player with the DataPlayer values
 */
fun DataPlayer.toPlayer() = Player(
    name, age
)