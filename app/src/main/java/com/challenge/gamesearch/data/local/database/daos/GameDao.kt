package com.challenge.gamesearch.data.local.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.challenge.gamesearch.data.local.database.models.GameDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    // TODO: Select a set size for return in cases where table may have to many rows

    @Query("SELECT count() FROM games")
    suspend fun count() : Int
    @Query("SELECT * FROM games")
    fun getGames() : Flow<List<GameDbEntity>>
    @Query("SELECT * FROM games where id=:gameId")
    fun getGamesById(gameId: String) : Flow<GameDbEntity?>
    @Query("SELECT * FROM games WHERE title LIKE '%'||:title||'%'")
    fun filterByTitle(title: String) : Flow<List<GameDbEntity>>
    @Query("SELECT * FROM games WHERE genre LIKE '%'||:genre||'%'")
    fun filterByGenre(genre: String) : Flow<List<GameDbEntity>>
    @Query("SELECT * FROM games WHERE platform LIKE '%'||:platform||'%'")
    fun filterByPlatform(platform: String) : Flow<List<GameDbEntity>>
    @Query("SELECT * FROM games WHERE developer LIKE '%'||:developer||'%'")
    fun filterByDeveloper(developer: String) : Flow<List<GameDbEntity>>
    @Query("SELECT * FROM games WHERE publisher LIKE '%'||:publisher||'%'")
    fun filterByPublisher(publisher: String) : Flow<List<GameDbEntity>>
    @Delete
    suspend fun delete(game: GameDbEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg games: GameDbEntity)
    @Update
    suspend fun update(game: GameDbEntity)
}