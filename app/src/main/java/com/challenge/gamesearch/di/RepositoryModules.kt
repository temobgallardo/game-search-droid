package com.challenge.gamesearch.di

import com.challenge.gamesearch.data.repositories.GameRepository
import com.challenge.gamesearch.domain.repositories.IGameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModules {
    @Binds
    @Singleton
    // What we do this abstract? Due to a base setup or configuration of the repository that can be
    // inherited but changed in the implementers
    abstract fun provideRepository(repositoryImpl : GameRepository) : IGameRepository
}