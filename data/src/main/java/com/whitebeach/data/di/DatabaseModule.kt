package com.whitebeach.data.di

import android.content.Context
import androidx.room.Room
import com.whitebeach.data.local.dao.PlayerDao
import com.whitebeach.data.local.database.AtleticoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "atletico_database"

    @Provides
    @Singleton
    fun provideAtleticoDatabase(
        @ApplicationContext context: Context,
    ): AtleticoDatabase {
        return Room.databaseBuilder(
            context,
            AtleticoDatabase::class.java,
            DATABASE_NAME,
        ).build()
    }

    @Provides
    fun providePlayerDao(
        database: AtleticoDatabase,
    ): PlayerDao {
        return database.playerDao()
    }
}