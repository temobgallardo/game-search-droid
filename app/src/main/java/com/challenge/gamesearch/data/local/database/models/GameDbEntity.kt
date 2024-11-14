package com.challenge.gamesearch.data.local.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("games")
data class GameDbEntity(
    @PrimaryKey()
    val id: String,
    val title: String,
    val thumbnail: String,
    val description: String,
    val url: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    @ColumnInfo("free_to_game_url")
    val freeToGameUrl: String
)
