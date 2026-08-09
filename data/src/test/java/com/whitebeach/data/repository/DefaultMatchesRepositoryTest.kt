package com.whitebeach.data.repository

import com.whitebeach.data.local.dao.MatchDao
import com.whitebeach.data.local.entity.MatchEntity
import com.whitebeach.data.remote.api.AtleticoApi
import com.whitebeach.data.remote.datasource.MatchesRemoteDataSource
import com.whitebeach.data.remote.dto.MatchDto
import com.whitebeach.data.remote.dto.PlayerDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * RepositoryTest
 * → Remote → Mapper → DAO
 */
class DefaultMatchesRepositoryTest {

    @Test
    fun `observeMatches converts entities to domain models`() = runTest {
        val dao = FakeMatchDao(
            initialMatches = listOf(
                MatchEntity(
                    id = 1,
                    competition = "LaLiga",
                    homeTeam = "Atlético Madrid",
                    awayTeam = "Real Madrid",
                    date = "2026-09-20",
                    time = "21:00",
                    status = "UPCOMING",
                    homeScore = null,
                    awayScore = null,
                ),
            ),
        )

        val repository = DefaultMatchesRepository(
            remoteDataSource = MatchesRemoteDataSource(
                api = FakeMatchesApi(),
            ),
            matchDao = dao,
        )

        val result = repository.observeMatches().first()

        assertEquals(1, result.size)
        assertEquals("LaLiga", result[0].competition)
        assertEquals("Atlético Madrid", result[0].homeTeam)
    }

    @Test
    fun `getMatchById returns mapped domain match`() = runTest {
        val dao = FakeMatchDao(
            initialMatches = listOf(
                MatchEntity(
                    id = 1,
                    competition = "LaLiga",
                    homeTeam = "Atlético Madrid",
                    awayTeam = "Real Madrid",
                    date = "2026-09-20",
                    time = "21:00",
                    status = "UPCOMING",
                    homeScore = null,
                    awayScore = null,
                ),
            ),
        )

        val repository = DefaultMatchesRepository(
            remoteDataSource = MatchesRemoteDataSource(
                api = FakeMatchesApi(),
            ),
            matchDao = dao,
        )

        val result = repository.getMatchById(1)

        assertEquals("LaLiga", result?.competition)
        assertEquals("Real Madrid", result?.awayTeam)
    }

    @Test
    fun `refreshMatches fetches remote matches and saves them to dao`() = runTest {
        val dao = FakeMatchDao()

        val api = FakeMatchesApi(
            matches = listOf(
                MatchDto(
                    id = 100,
                    competition = "Champions League",
                    homeTeam = "Atlético Madrid",
                    awayTeam = "Arsenal",
                    date = "2026-10-01",
                    time = "21:00",
                    status = "UPCOMING",
                    homeScore = null,
                    awayScore = null,
                ),
            ),
        )

        val repository = DefaultMatchesRepository(
            remoteDataSource = MatchesRemoteDataSource(api),
            matchDao = dao,
        )

        repository.refreshMatches()

        val savedMatches =
            dao.observeMatches().first()

        assertEquals(1, savedMatches.size)

        assertEquals(
            MatchEntity(
                id = 100,
                competition = "Champions League",
                homeTeam = "Atlético Madrid",
                awayTeam = "Arsenal",
                date = "2026-10-01",
                time = "21:00",
                status = "UPCOMING",
                homeScore = null,
                awayScore = null,
            ),
            savedMatches.first(),
        )
    }
}

private class FakeMatchDao(
    initialMatches: List<MatchEntity> = emptyList(),
) : MatchDao {

    private val matches =
        MutableStateFlow(initialMatches)

    override fun observeMatches(): Flow<List<MatchEntity>> {
        return matches
    }

    override fun observeMatchById(matchId: Int): Flow<MatchEntity?> {
        return MutableStateFlow(
            matches.value.firstOrNull {
                it.id == matchId
            },
        )
    }

    override suspend fun getMatchById(
        matchId: Int,
    ): MatchEntity? {
        return matches.value.firstOrNull {
            it.id == matchId
        }
    }

    override suspend fun upsertMatches(
        matches: List<MatchEntity>,
    ) {
        val current =
            this.matches.value.associateBy { it.id }
                .toMutableMap()

        matches.forEach { match ->
            current[match.id] = match
        }

        this.matches.value =
            current.values.toList()
    }

    override suspend fun deleteAllMatches() {
        matches.value = emptyList()
    }

    override suspend fun getMatchCount(): Int {
        return matches.value.size
    }
}

private class FakeMatchesApi(
    private val matches: List<MatchDto> = emptyList(),
) : AtleticoApi {

    override suspend fun getPlayers(): List<PlayerDto> {
        return emptyList()
    }

    override suspend fun getMatches(): List<MatchDto> {
        return matches
    }
}