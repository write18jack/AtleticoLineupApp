package com.whitebeach.data.repository

import com.whitebeach.data.model.AtleticoPlayer
import com.whitebeach.data.remote.network.PlayersInfoService
import retrofit2.Response
import javax.inject.Inject

class PlayersRepository2 @Inject constructor(
    private val playersInfoService2: PlayersInfoService
) {

    suspend fun getPlayers2(): Response<AtleticoPlayer> {
        return playersInfoService2.getPlayers()
    }

}