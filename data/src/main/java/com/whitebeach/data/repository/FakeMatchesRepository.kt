package com.whitebeach.data.repository

import com.whitebeach.data.datasource.dummy.dummyMatches
import com.whitebeach.domain.model.Match
import com.whitebeach.domain.repository.MatchesRepository
import javax.inject.Inject

class FakeMatchesRepository @Inject constructor() : MatchesRepository {

    override suspend fun getMatches(): List<Match> {
        return dummyMatches
    }
}