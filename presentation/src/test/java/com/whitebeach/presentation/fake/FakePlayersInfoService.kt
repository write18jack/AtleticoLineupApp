package com.whitebeach.presentation.fake

import com.google.gson.Gson
import com.whitebeach.data.model.player.PlayersResponse
import com.whitebeach.data.remote.network.PlayersInfoService
import com.whitebeach.presentation.util.AssetsManager
import retrofit2.Response
import java.io.InputStreamReader

class FakePlayersInfoService : PlayersInfoService {
    override suspend fun getPlayers(): Response<PlayersResponse> {
        return Response.success(getPlayersResponse())
    }

    override suspend fun getPlayers2(): Response<PlayersResponse> {
        return Response.success(getPlayersResponse())
    }

    override suspend fun getPlayers3(): Response<PlayersResponse> {
        return Response.success(getPlayersResponse())
    }

    fun getPlayersResponse(): PlayersResponse? {
        return AssetsManager.openStream("players_data.json")
            ?.use { stream ->
            val gson = Gson()
            val reader = InputStreamReader(stream)
            gson.fromJson(reader, PlayersResponse::class.java)
        }
    }
}
