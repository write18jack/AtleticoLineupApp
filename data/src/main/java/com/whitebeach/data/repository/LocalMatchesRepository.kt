package com.whitebeach.data.repository

import com.whitebeach.data.datasource.dummy.dummyMatches
import com.whitebeach.data.local.dao.MatchDao
import com.whitebeach.data.local.mapper.toDomain
import com.whitebeach.data.local.mapper.toDomainModels
import com.whitebeach.data.local.mapper.toEntities
import com.whitebeach.domain.model.Match
import com.whitebeach.domain.repository.MatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalMatchesRepository @Inject constructor(
    private val matchDao: MatchDao,
) : MatchesRepository {

    override fun observeMatches(): Flow<List<Match>> {
        return flow {
            seedMatchesIfEmpty()

            emitAll(
                matchDao.observeMatches()
                    .map { entities ->
                        entities.toDomainModels()
                    },
            )
        }
    }

    override suspend fun getMatchById(
        matchId: Int,
    ): Match? {
        seedMatchesIfEmpty()

        return matchDao
            .getMatchById(matchId)
            ?.toDomain()
    }

    suspend fun saveMatches(
        matches: List<Match>,
    ) {
        matchDao.upsertMatches(
            matches.toEntities(),
        )
    }

    suspend fun deleteAllMatches() {
        matchDao.deleteAllMatches()
    }

    private suspend fun seedMatchesIfEmpty() {
        if (matchDao.getMatchCount() == 0) {
            matchDao.upsertMatches(
                dummyMatches.toEntities(),
            )
        }
    }
}