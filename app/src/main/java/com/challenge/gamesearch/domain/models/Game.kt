package com.challenge.gamesearch.domain.models

data class Game(
    val id: String,
    val title: String,
    val thumbnail: String,
    val description: String,
    val url: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    val freeToGameUrl: String
)
