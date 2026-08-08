package com.whitebeach.data.repository

import com.whitebeach.data.local.dao.PlayerDao
import com.whitebeach.data.local.mapper.toDomain
import com.whitebeach.data.local.mapper.toDomainModels as entitiesToDomain
import com.whitebeach.data.local.mapper.toEntities
import com.whitebeach.data.remote.datasource.PlayersRemoteDataSource
import com.whitebeach.data.remote.mapper.toDomainModels as dtoToDomain
import com.whitebeach.domain.model.Player
import com.whitebeach.domain.repository.PlayersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultPlayersRepository @Inject constructor(
    private val remoteDataSource: PlayersRemoteDataSource,
    private val playerDao: PlayerDao,
) : PlayersRepository {

    override fun observePlayers(): Flow<List<Player>> {
        return playerDao.observePlayers()
            .map { entities ->
                entities.entitiesToDomain()
            }
    }

    override suspend fun getPlayerById(
        playerId: Int,
    ): Player? {
        return playerDao
            .getPlayerById(playerId)
            ?.toDomain()
    }

    override suspend fun refreshPlayers() {
        val remotePlayers = remoteDataSource
            .getPlayers()
            .dtoToDomain()

        playerDao.upsertPlayers(
            players = remotePlayers.toEntities(),
        )
    }
}