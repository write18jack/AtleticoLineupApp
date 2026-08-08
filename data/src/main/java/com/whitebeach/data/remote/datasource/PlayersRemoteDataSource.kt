package com.whitebeach.data.remote.datasource

import com.whitebeach.data.remote.api.AtleticoApi
import com.whitebeach.data.remote.dto.PlayerDto
import javax.inject.Inject

class PlayersRemoteDataSource @Inject constructor(
    private val api: AtleticoApi,
) {

    suspend fun getPlayers(): List<PlayerDto> {
        return api.getPlayers()
    }
}