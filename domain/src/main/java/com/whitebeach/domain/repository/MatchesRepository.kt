package com.whitebeach.domain.repository

import com.whitebeach.domain.model.Match

interface MatchesRepository {

    suspend fun getMatches(): List<Match>

    suspend fun getMatchById(matchId: Int): Match?
}