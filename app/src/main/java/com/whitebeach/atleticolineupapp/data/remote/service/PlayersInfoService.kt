package com.whitebeach.atleticolineupapp.data.remote.service

import com.whitebeach.atleticolineupapp.data.model.remote.player.PlayersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface PlayersInfoService {
    @Headers(
        value = [
            "X-RapidAPI-Key: b00f4bb156msh43f2a2457d9585ap1fdcd3jsn9af217826811",
            "X-RapidAPI-Host: api-football-v1.p.rapidapi.com"
        ]
    )
    @GET("v3/players?team=530&league=140&season=2024&page=1")
    suspend fun getPlayers(): Response<PlayersResponse>

    @Headers(
        value = [
            "X-RapidAPI-Key: b00f4bb156msh43f2a2457d9585ap1fdcd3jsn9af217826811",
            "X-RapidAPI-Host: api-football-v1.p.rapidapi.com"
        ]
    )
    @GET("v3/players?team=530&league=140&season=2024&page=2")
    suspend fun getPlayers2(): Response<PlayersResponse>

    @Headers(
        value = [
            "X-RapidAPI-Key: b00f4bb156msh43f2a2457d9585ap1fdcd3jsn9af217826811",
            "X-RapidAPI-Host: api-football-v1.p.rapidapi.com"
        ]
    )
    @GET("v3/players?team=530&league=140&season=2024&page=3")
    suspend fun getPlayers3(): Response<PlayersResponse>
}