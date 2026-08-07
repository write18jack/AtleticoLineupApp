package com.whitebeach.domain.repository

import com.whitebeach.domain.model.Player
import kotlinx.coroutines.flow.Flow

// Repositoryは「約束」だけ
interface PlayersRepository {

    fun observePlayers(): Flow<List<Player>>

    suspend fun getPlayerById(playerId: Int): Player?
}