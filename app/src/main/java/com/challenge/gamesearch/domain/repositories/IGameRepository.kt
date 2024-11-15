package com.challenge.gamesearch.domain.repositories

import com.challenge.gamesearch.data.repositories.GameFilters
import com.challenge.gamesearch.domain.models.Game
import kotlinx.coroutines.flow.Flow

interface IGameRepository {
    // Get games from db
    fun getGames(): Flow<List<Game>>
    fun getGameById(gameId: String): Flow<Game>
    fun filterBy(toSearch: String, filter: GameFilters): Flow<List<Game>>
    suspend fun delete(game: Game)
    suspend fun insert(vararg games: Game)
    suspend fun update(game: Game)
    // Fetch first form db and if not value get from web api
    suspend fun fetchOrRequestGames(): String?
}