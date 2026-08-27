package com.whitebeach.data.remote.api

import com.whitebeach.data.remote.dto.MatchDto
import com.whitebeach.data.remote.dto.PlayerDto
import retrofit2.http.GET

interface AtleticoApi {

    @GET("players")
    suspend fun getPlayers(): List<PlayerDto>

    @GET("matches")
    suspend fun getMatches(): List<MatchDto>
}