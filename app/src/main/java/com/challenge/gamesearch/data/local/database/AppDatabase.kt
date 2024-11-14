package com.challenge.gamesearch.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.challenge.gamesearch.data.local.database.daos.GameDao
import com.challenge.gamesearch.data.local.database.models.GameDbEntity

@Database(entities = [GameDbEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getGameDao() : GameDao
}