package com.challenge.gamesearch.data.mappers

import com.challenge.gamesearch.data.local.database.models.GameDbEntity
import com.challenge.gamesearch.data.models.GameDto
import com.challenge.gamesearch.domain.models.Game

fun GameDto.toDomain() : Game = Game(
    id = this.id,
    title = this.title,
    thumbnail = this.thumbnail,
    description = this.shortDescription,
    url = this.gameUrl,
    genre = this.genre,
    platform = this.platform,
    publisher = this.publisher,
    developer = this.developer,
    freeToGameUrl = this.freeToGameProfileUrl
)

fun Game.toEntity() : GameDbEntity = GameDbEntity(
    id = this.id,
    title = this.title,
    thumbnail = this.thumbnail,
    description = this.description,
    url = this.url,
    genre = this.genre,
    platform = this.platform,
    publisher = this.publisher,
    developer = this.developer,
    freeToGameUrl = this.freeToGameUrl
)

fun GameDbEntity.toDomain() : Game = Game(
    id = this.id,
    title = this.title,
    thumbnail = this.thumbnail,
    description = this.description,
    url = this.url,
    genre = this.genre,
    platform = this.platform,
    publisher = this.publisher,
    developer = this.developer,
    freeToGameUrl = this.freeToGameUrl
)