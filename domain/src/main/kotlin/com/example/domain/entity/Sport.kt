package com.example.domain.entity

data class Sport(
    val name: String,
    val numOfPlayers: Int
)

val listOfPredefinedSports = listOf(
    Sport("Football", 11),
    Sport("Basketball", 5),
    Sport("Volley ball", 7)
)

fun takeRandomSport() = listOfPredefinedSports[(listOfPredefinedSports.indices).random()]
