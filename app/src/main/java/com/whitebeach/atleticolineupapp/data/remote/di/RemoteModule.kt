package com.whitebeach.atleticolineupapp.data.remote.di

import com.squareup.moshi.Moshi
import com.whitebeach.atleticolineupapp.data.remote.service.PlayersInfoService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RemoteModule {
}

private const val BASE_URL = "https://api-football-v1.p.rapidapi.com"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(
        MoshiConverterFactory.create(
            Moshi.Builder().add(MoshiConverterFactory.create()).build()
        )
    )
    .baseUrl(BASE_URL)
    .build()

object PlayerApi {
    val retrofitService: PlayersInfoService by lazy {
        retrofit.create(PlayersInfoService::class.java)
    }
}