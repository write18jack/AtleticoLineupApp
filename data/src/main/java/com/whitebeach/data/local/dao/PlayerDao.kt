package com.whitebeach.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.whitebeach.data.local.entity.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Query("""SELECT * FROM players ORDER BY
            CASE position
                WHEN 'GOALKEEPER' THEN 1
                WHEN 'DEFENDER' THEN 2
                WHEN 'MIDFIELDER' THEN 3
                WHEN 'FORWARD' THEN 4
                ELSE 5
            END,
            shirtNumber ASC, name ASC""")
    fun observePlayers(): Flow<List<PlayerEntity>>

    @Query("""SELECT * FROM players WHERE id = :playerId LIMIT 1""" )
    fun observePlayerById(
        playerId: Int,
    ): Flow<PlayerEntity?>

    @Query("""SELECT * FROM players WHERE id = :playerId LIMIT 1""" )
    suspend fun getPlayerById(
        playerId: Int,
    ): PlayerEntity?

    @Upsert
    suspend fun upsertPlayers(
        players: List<PlayerEntity>,
    )

    @Query("DELETE FROM players")
    suspend fun deleteAllPlayers()

    @Query("SELECT COUNT(*) FROM players")
    suspend fun getPlayerCount(): Int
}