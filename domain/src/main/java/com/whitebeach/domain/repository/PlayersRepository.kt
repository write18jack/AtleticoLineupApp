package com.whitebeach.domain.repository

import com.whitebeach.domain.model.Player

// Repositoryは「約束」だけ
interface PlayersRepository {

    suspend fun getPlayers(): List<Player>

    suspend fun getPlayerById(playerId: Int): Player?
}