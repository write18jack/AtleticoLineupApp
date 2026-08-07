package com.whitebeach.data.di

import com.whitebeach.data.repository.LocalMatchesRepository
import com.whitebeach.data.repository.LocalPlayersRepository
import com.whitebeach.domain.repository.MatchesRepository
import com.whitebeach.domain.repository.PlayersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPlayersRepository(
        repository: LocalPlayersRepository
    ): PlayersRepository

    @Binds
    @Singleton
    abstract fun bindMatchesRepository(
        repository: LocalMatchesRepository
    ): MatchesRepository
}