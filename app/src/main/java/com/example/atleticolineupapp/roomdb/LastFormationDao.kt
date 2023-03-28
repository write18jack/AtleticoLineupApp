package com.example.atleticolineupapp.roomdb

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LastFormationDao {
    @Query("SELECT * from LastFormation")
    fun getFormation(): Flow<LastFormation>

    @Query("SELECT * from LastFormation WHERE id = :id")
    fun getFormationId(id: Int): Flow<LastFormation>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFormation(item: LastFormation)

    @Update
    suspend fun updateFormation(item: LastFormation)
}