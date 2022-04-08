package com.example.domain.entity

data class Player(
    val name: String,
    val age: Int
)

fun generateRandomPlayer(number: Int) = Player(
    "Player $number",
    (18..65).random()
)