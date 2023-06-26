package com.example.atleticolineupapp.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LineupDao {
    @Query("SELECT * from Lineup ORDER BY position ASC")
    fun getItems(): Flow<List<Lineup>>

    // 全てのアイテム取得 LiveData
    @Query("SELECT * FROM Lineup")
    fun selectAllWithLiveData(): LiveData<List<Lineup>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Lineup)

    @Update
    suspend fun update(item: Lineup)
}