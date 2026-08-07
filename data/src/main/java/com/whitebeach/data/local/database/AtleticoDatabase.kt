package com.whitebeach.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.whitebeach.data.local.dao.PlayerDao
import com.whitebeach.data.local.entity.PlayerEntity

@Database(
    entities = [
        PlayerEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class AtleticoDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao
}