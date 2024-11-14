package com.challenge.gamesearch.di

import android.content.Context
import androidx.room.Room
import com.challenge.gamesearch.data.local.database.AppDatabase
import com.challenge.gamesearch.data.local.database.daos.GameDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModules {
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context):
            AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "my_games.db").build()

    @Provides
    @Singleton
    fun providesGameDao(appDatabase : AppDatabase) : GameDao = appDatabase.getGameDao()

}