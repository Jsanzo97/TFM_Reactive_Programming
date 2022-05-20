package com.example.data.entity

import com.example.domain.entity.Sport

/**
 * Represent sport in the system
 * @param name name of the sport
 * @param numOfPlayers number of players in the sport
 */
class DataSport (
    val name: String,
    val numOfPlayers: Int
)

/**
 * Convert Sport to DataSport
 * @return new DataSport with the Sport values
 */
fun Sport.toDataSport() = DataSport(
    name, numOfPlayers
)

/**
 * Convert DataSport to Sport
 * @return new Sport with the DataSport values
 */
fun DataSport.toSport() = Sport(
    name, numOfPlayers
)