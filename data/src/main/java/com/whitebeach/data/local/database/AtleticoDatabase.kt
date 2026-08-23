package com.whitebeach.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.whitebeach.data.local.dao.MatchDao
import com.whitebeach.data.local.dao.PlayerDao
import com.whitebeach.data.local.entity.MatchEntity
import com.whitebeach.data.local.entity.PlayerEntity

@Database(
    entities = [
        PlayerEntity::class,
        MatchEntity::class
    ],
    version = 3,
    exportSchema = true,
)
abstract class AtleticoDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao

    abstract fun matchDao(): MatchDao
}