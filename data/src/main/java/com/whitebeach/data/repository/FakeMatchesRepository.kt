package com.whitebeach.data.repository

import com.whitebeach.data.datasource.dummy.dummyMatches
import com.whitebeach.domain.model.Match
import com.whitebeach.domain.repository.MatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeMatchesRepository @Inject constructor() : MatchesRepository {

    override fun observeMatches(): Flow<List<Match>> {
        return flowOf()
    }

    override suspend fun getMatchById(matchId: Int): Match? {
        return dummyMatches.firstOrNull { match ->
            match.id == matchId
        }
    }
}