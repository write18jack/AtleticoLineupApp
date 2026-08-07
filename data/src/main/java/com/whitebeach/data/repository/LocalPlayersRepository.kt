package com.whitebeach.data.repository

import com.whitebeach.data.datasource.dummy.dummyPlayers
import com.whitebeach.data.local.dao.PlayerDao
import com.whitebeach.data.local.mapper.toDomain
import com.whitebeach.data.local.mapper.toDomainModels
import com.whitebeach.data.local.mapper.toEntities
import com.whitebeach.domain.model.Player
import com.whitebeach.domain.repository.PlayersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalPlayersRepository @Inject constructor(
    private val playerDao: PlayerDao,
) : PlayersRepository {

    override fun observePlayers(): Flow<List<Player>> {
        return flow {
            seedPlayersIfEmpty()

            emitAll(
                playerDao.observePlayers()
                    .map { entities ->
                        entities.toDomainModels()
                    },
            )
        }
    }

    override suspend fun getPlayerById(
        playerId: Int,
    ): Player? {
        seedPlayersIfEmpty()

        return playerDao
            .getPlayerById(playerId)
            ?.toDomain()
    }

    suspend fun savePlayers(
        players: List<Player>,
    ) {
        playerDao.upsertPlayers(
            players = players.toEntities(),
        )
    }

    suspend fun deleteAllPlayers() {
        playerDao.deleteAllPlayers()
    }

    private suspend fun seedPlayersIfEmpty() {
        if (playerDao.getPlayerCount() == 0) {
            playerDao.upsertPlayers(
                players = dummyPlayers.toEntities(),
            )
        }
    }
}