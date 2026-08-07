package com.whitebeach.domain.repository

import com.whitebeach.domain.model.Match
import kotlinx.coroutines.flow.Flow

interface MatchesRepository {

    fun observeMatches(): Flow<List<Match>>

    suspend fun getMatchById(matchId: Int): Match?
}