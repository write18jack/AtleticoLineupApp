package com.whitebeach.data.remote.datasource

import com.whitebeach.data.remote.api.AtleticoApi
import com.whitebeach.data.remote.dto.MatchDto
import javax.inject.Inject

class MatchesRemoteDataSource @Inject constructor(
    private val api: AtleticoApi,
) {

    suspend fun getMatches(): List<MatchDto> {
        return api.getMatches()
    }
}