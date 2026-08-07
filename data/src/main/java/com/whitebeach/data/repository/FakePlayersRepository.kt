package com.whitebeach.data.repository

import com.whitebeach.data.datasource.dummy.dummyPlayers
import com.whitebeach.domain.model.Player
import com.whitebeach.domain.repository.PlayersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FakePlayersRepository @Inject constructor() : PlayersRepository {

    override fun observePlayers(): Flow<List<Player>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayerById(playerId: Int): Player? {
        return dummyPlayers.firstOrNull { player ->
            player.id == playerId
        }
    }
}