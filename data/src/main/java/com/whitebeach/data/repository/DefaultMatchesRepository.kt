package com.whitebeach.data.repository

import com.whitebeach.data.local.dao.MatchDao
import com.whitebeach.data.local.mapper.toDomain
import com.whitebeach.data.local.mapper.toDomainMatches
import com.whitebeach.data.local.mapper.toEntities
import com.whitebeach.data.remote.datasource.MatchesRemoteDataSource
import com.whitebeach.data.remote.mapper.toDomainMatches
import com.whitebeach.domain.model.Match
import com.whitebeach.domain.repository.MatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultMatchesRepository @Inject constructor(
    private val remoteDataSource: MatchesRemoteDataSource,
    private val matchDao: MatchDao,
) : MatchesRepository {

    override fun observeMatches(): Flow<List<Match>> {
        return matchDao.observeMatches()
            .map { entities ->
                entities.toDomainMatches()
            }
    }

    override suspend fun getMatchById(
        matchId: Int,
    ): Match? {
        return matchDao
            .getMatchById(matchId)
            ?.toDomain()
    }

    override suspend fun refreshMatches() {
        val remoteMatches = remoteDataSource
            .getMatches()
            .toDomainMatches()

        matchDao.upsertMatches(
            matches = remoteMatches.toEntities(),
        )
    }
}