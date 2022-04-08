package com.example.data.entity

import com.example.domain.entity.Player

data class DataPlayer (
    val name: String,
    val age: Int
)

fun Player.toDataPlayer() = DataPlayer(
    name, age
)

fun DataPlayer.toPlayer() = Player(
    name, age
)