package com.whitebeach.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.whitebeach.data.local.entity.MatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {

    @Query("""SELECT * FROM matches ORDER BY date ASC, time ASC""")
    fun observeMatches(): Flow<List<MatchEntity>>

    @Query("""SELECT * FROM matches WHERE id = :matchId LIMIT 1""")
    fun observeMatchById(matchId: Int): Flow<MatchEntity?>

    @Query("""SELECT * FROM matches WHERE id = :matchId LIMIT 1""")
    suspend fun getMatchById(matchId: Int): MatchEntity?

    @Upsert
    suspend fun upsertMatches(matches: List<MatchEntity>)

    @Query("DELETE FROM matches")
    suspend fun deleteAllMatches()

    @Query("SELECT COUNT(*) FROM matches")
    suspend fun getMatchCount(): Int
}