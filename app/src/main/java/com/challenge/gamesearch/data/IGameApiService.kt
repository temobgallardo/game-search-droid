package com.challenge.gamesearch.data

import com.challenge.gamesearch.data.models.GameDto
import retrofit2.http.GET

interface IGameApiService {
    @GET("games")
    suspend fun getGames() : List<GameDto>
}