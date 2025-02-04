package com.whitebeach.data.remote

import com.squareup.moshi.Moshi
import com.whitebeach.data.model.player.Paging
import com.whitebeach.data.model.player.Parameters
import com.whitebeach.data.model.player.PlayersResponse
import com.whitebeach.data.remote.network.PlayersInfoService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api-football-v1.p.rapidapi.com"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(
        MoshiConverterFactory.create(
            Moshi.Builder().add(MoshiConverterFactory.create()).build()
        )
    )
    .baseUrl(BASE_URL)
    .build()


interface PlayerApi {
    val retrofitService: PlayersInfoService
}
class PlayerApiImpl(): PlayerApi {
    override val retrofitService: PlayersInfoService by lazy {
        retrofit.create(PlayersInfoService::class.java)
    }
}

class FakePlayerApi(
    fakePlayersInfoService: FakePlayersInfoService
): PlayerApi {
    override val retrofitService: PlayersInfoService = fakePlayersInfoService
}

class FakePlayersInfoService: PlayersInfoService {

    var getPlayersImpl: () -> Response<PlayersResponse> =  {
        Response.success(
            PlayersResponse(
                `get` = "get",
                parameters = Parameters(
                    team = "team",
                    season = "season"
                ),
                errors = emptyList(),
                results = 1,
                paging = Paging(
                    current = 1,
                    total = 1
                ),
                response = emptyList(),
            )
        )
    }
    var getPlayers2Impl: () -> Response<PlayersResponse> =  {
        Response.success(
            PlayersResponse(
                `get` = "get",
                parameters = Parameters(
                    team = "team",
                    season = "season"
                ),
                errors = emptyList(),
                results = 1,
                paging = Paging(
                    current = 1,
                    total = 1
                ),
                response = emptyList(),
            )
        )
    }
    var getPlayers3Impl: () -> Response<PlayersResponse> =  {
        Response.success(
            PlayersResponse(
                `get` = "get",
                parameters = Parameters(
                    team = "team",
                    season = "season"
                ),
                errors = emptyList(),
                results = 1,
                paging = Paging(
                    current = 1,
                    total = 1
                ),
                response = emptyList(),
            )
        )
    }

    override suspend fun getPlayers(): Response<PlayersResponse> {
        return getPlayersImpl()
    }

    override suspend fun getPlayers2(): Response<PlayersResponse> {
        return getPlayers2Impl()
    }

    override suspend fun getPlayers3(): Response<PlayersResponse> {
        return getPlayers3Impl()
    }
}