package com.whitebeach.data.remote.network

import com.whitebeach.data.model.AtleticoPlayer
import retrofit2.Response
import retrofit2.http.GET

interface PlayersInfoService {
    @GET("v4/teams/78")
    suspend fun getPlayers(): Response<AtleticoPlayer>
}