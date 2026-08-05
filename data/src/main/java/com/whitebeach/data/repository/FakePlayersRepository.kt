package com.whitebeach.data.repository

import com.whitebeach.data.datasource.dummy.dummyPlayers
import com.whitebeach.domain.model.Player
import com.whitebeach.domain.repository.PlayersRepository
import javax.inject.Inject

class FakePlayersRepository @Inject constructor() : PlayersRepository {

    override suspend fun getPlayers(): List<Player> {
        return dummyPlayers
    }

    override suspend fun getPlayerById(playerId: Int): Player? {
        return dummyPlayers.firstOrNull { player ->
            player.id == playerId
        }
    }
}