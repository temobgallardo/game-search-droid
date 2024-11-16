package com.challenge.gamesearch.data.repositories

import android.util.Log
import com.challenge.gamesearch.data.IGameApiService
import com.challenge.gamesearch.data.local.database.daos.GameDao
import com.challenge.gamesearch.data.mappers.toDomain
import com.challenge.gamesearch.data.mappers.toEntity
import com.challenge.gamesearch.domain.models.Game
import com.challenge.gamesearch.domain.repositories.IGameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GameRepository @Inject constructor(
    private val gameApiService: IGameApiService,
    private val gameDao: GameDao
    // TODO: Inject logger abstraction
) : IGameRepository {
    override fun getGames(): Flow<List<Game>> = gameDao.getGames().map { games ->
        games.map { game ->
            game.toDomain()
        }
    }

    override fun getGameById(gameId: String): Flow<Game?> =
        gameDao.getGamesById(gameId).map { game ->
            game?.toDomain()
        }

    override suspend fun delete(game: Game) {
        gameDao.delete(game.toEntity())
        // TODO: use abstraction for logging
        Log.d("INFO", "Deleted game: $game")
    }

    override suspend fun insert(vararg games: Game) {
        gameDao.insert(*games.map { game -> game.toEntity() }.toTypedArray())
        // TODO: use abstraction for logging
        Log.d("INFO", "Inserted game(s): $games")
    }

    override suspend fun update(game: Game) {
        gameDao.update(game.toEntity())
        // TODO: use abstraction for logging
        Log.d("INFO", "Deleted game: $game")
    }

    override suspend fun fetchOrRequestGames(): String? {
        if (gameDao.count() > 0) return null

        try {
            val games = gameApiService.getGames().map { g -> g.toDomain() }
            val gameEntities = games.map { game -> game.toEntity() }.toTypedArray()
            gameDao.insert(*gameEntities)
            // TODO: use abstraction for logging
            Log.d("INFO", games.toString())
        } catch (e: Exception) {
            return e.message.toString()
        }

        return null
    }

    private fun filterByTitle(title: String): Flow<List<Game>> =
        gameDao.filterByTitle(title).map { games ->
            games.map { game ->
                game.toDomain()
            }
        }

    private fun filterByGenre(genre: String): Flow<List<Game>> =
        gameDao.filterByGenre(genre).map { games ->
            games.map { game ->
                game.toDomain()
            }
        }

    private fun filterByPlatform(platform: String): Flow<List<Game>> =
        gameDao.filterByPlatform(platform).map { games ->
            games.map { game ->
                game.toDomain()
            }
        }

    private fun filterByDeveloper(developer: String): Flow<List<Game>> =
        gameDao.filterByDeveloper(developer).map { games ->
            games.map { game ->
                game.toDomain()
            }
        }

    private fun filterByPublisher(publisher: String): Flow<List<Game>> =
        gameDao.filterByPublisher(publisher).map { games ->
            games.map { game ->
                game.toDomain()
            }
        }
}

enum class GameFilters {
    Title,
    Genre,
    Platform,
    Developer,
    Publisher,
    All
}