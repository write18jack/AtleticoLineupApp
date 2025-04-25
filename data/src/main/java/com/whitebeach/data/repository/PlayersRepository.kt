package com.whitebeach.data.repository

import com.whitebeach.data.model.player.PlayersResponse
import com.whitebeach.data.remote.network.PlayersInfoService
import retrofit2.Response
import javax.inject.Inject

class PlayersRepository @Inject constructor(
    private val playersInfoService: PlayersInfoService
) {

    suspend fun getPlayers(): Response<PlayersResponse> {
        return playersInfoService.getPlayers()
    }

    suspend fun getPlayers2(): Response<PlayersResponse> {
        return playersInfoService.getPlayers2()
    }

    suspend fun getPlayers3(): Response<PlayersResponse> {
        return playersInfoService.getPlayers3()
    }

}
