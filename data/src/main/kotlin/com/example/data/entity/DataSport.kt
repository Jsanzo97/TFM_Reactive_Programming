package com.example.data.entity

import com.example.domain.entity.Sport

class DataSport (
    val name: String,
    val numOfPlayers: Int
)

fun Sport.toDataSport() = DataSport(
    name, numOfPlayers
)

fun DataSport.toSport() = Sport(
    name, numOfPlayers
)