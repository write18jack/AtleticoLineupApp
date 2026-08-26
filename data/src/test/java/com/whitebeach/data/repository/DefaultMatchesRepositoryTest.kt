package com.whitebeach.data.repository

import com.whitebeach.data.local.dao.MatchDao
import com.whitebeach.data.local.entity.MatchEntity
import com.whitebeach.data.remote.api.AtleticoApi
import com.whitebeach.data.remote.datasource.MatchesRemoteDataSource
import com.whitebeach.data.remote.dto.MatchDto
import com.whitebeach.data.remote.dto.PlayerDto
import com.whitebeach.domain.model.MatchStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DefaultMatchesRepositoryTest {

    // Room読み込み
    @Test
    fun `observeMatches returns Room matches mapped to domain`() = runTest {
        val entities = listOf(
            createMatchEntity(
                id = 1,
                homeTeam = "Atlético de Madrid",
                awayTeam = "Málaga CF",
            ),
            createMatchEntity(
                id = 2,
                homeTeam = "Real Betis",
                awayTeam = "Atlético de Madrid",
            ),
        )

        val matchDao = FakeMatchDao(
            initialMatches = entities,
        )

        val remoteDataSource = MatchesRemoteDataSource(
            api = FakeMatchesAtleticoApi(),
        )

        val repository = DefaultMatchesRepository(
            remoteDataSource = remoteDataSource,
            matchDao = matchDao,
        )

        val actual = repository
            .observeMatches()
            .first()

        assertEquals(
            2,
            actual.size,
        )

        assertEquals(
            1,
            actual[0].id,
        )

        assertEquals(
            "Atlético de Madrid",
            actual[0].homeTeam,
        )

        assertEquals(
            "Málaga CF",
            actual[0].awayTeam,
        )

        assertEquals(
            MatchStatus.UPCOMING,
            actual[0].status,
        )
    }

    // ID検索
    @Test
    fun `getMatchById returns mapped domain match`() = runTest {
        val matchDao = FakeMatchDao(
            initialMatches = listOf(
                createMatchEntity(
                    id = 10,
                    competition = "LALIGA EA SPORTS",
                    matchDay = "Matchday 1",
                    venueName = "Riyadh Air Metropolitano",
                    venueCity = "Madrid",
                ),
            ),
        )

        val remoteDataSource = MatchesRemoteDataSource(
            api = FakeMatchesAtleticoApi(),
        )

        val repository = DefaultMatchesRepository(
            remoteDataSource = remoteDataSource,
            matchDao = matchDao,
        )

        val actual = repository.getMatchById(
            matchId = 10,
        )

        assertNotNull(actual)

        assertEquals(
            10,
            actual?.id,
        )

        assertEquals(
            "LALIGA EA SPORTS",
            actual?.competition,
        )

        assertEquals(
            "Matchday 1",
            actual?.matchDay,
        )

        assertEquals(
            "Riyadh Air Metropolitano",
            actual?.venueName,
        )

        assertEquals(
            "Madrid",
            actual?.venueCity,
        )
    }

    // ID検索一致するもの存在しない
    @Test
    fun `getMatchById returns null when match does not exist`() = runTest {
        val matchDao = FakeMatchDao()

        val remoteDataSource = MatchesRemoteDataSource(
            api = FakeMatchesAtleticoApi(),
        )

        val repository = DefaultMatchesRepository(
            remoteDataSource = remoteDataSource,
            matchDao = matchDao,
        )

        val actual = repository.getMatchById(
            matchId = 999,
        )

        assertNull(actual)
    }

    // Remote更新
    @Test
    fun `refreshMatches fetches remote matches and upserts them into Room`() = runTest {
        val remoteMatches = listOf(
            createMatchDto(
                id = 100,
                homeTeam = "Atlético de Madrid",
                awayTeam = "Málaga CF",
            ),
            createMatchDto(
                id = 101,
                homeTeam = "Atlético de Madrid",
                awayTeam = "FC Barcelona",
            ),
        )

        val remoteDataSource = MatchesRemoteDataSource(
            api = FakeMatchesAtleticoApi(
                matches = remoteMatches,
            ),
        )

        val matchDao = FakeMatchDao()

        val repository = DefaultMatchesRepository(
            remoteDataSource = remoteDataSource,
            matchDao = matchDao,
        )

        repository.refreshMatches()

        val storedMatches = matchDao
            .observeMatches()
            .first()

        assertEquals(
            2,
            storedMatches.size,
        )

        assertEquals(
            100,
            storedMatches[0].id,
        )

        assertEquals(
            "Atlético de Madrid",
            storedMatches[0].homeTeam,
        )

        assertEquals(
            "Málaga CF",
            storedMatches[0].awayTeam,
        )

        assertEquals(
            101,
            storedMatches[1].id,
        )

        assertEquals(
            "FC Barcelona",
            storedMatches[1].awayTeam,
        )
    }

    // API失敗時の例外伝播
    @Test
    fun `refreshMatches propagates remote exception`() = runTest {
        val remoteDataSource = MatchesRemoteDataSource(
            api = FakeMatchesAtleticoApi(
                exception = IllegalStateException(
                    "Backend unavailable",
                ),
            ),
        )

        val matchDao = FakeMatchDao()

        val repository = DefaultMatchesRepository(
            remoteDataSource = remoteDataSource,
            matchDao = matchDao,
        )

        val result = runCatching {
            repository.refreshMatches()
        }

        assertTrue(
            result.exceptionOrNull() is IllegalStateException,
        )

        assertEquals(
            "Backend unavailable",
            result.exceptionOrNull()?.message,
        )
    }

    // キャッシュ保持
    @Test
    fun `refreshMatches does not overwrite cached matches when remote fails`() = runTest {
        val cachedMatch = createMatchEntity(
            id = 1,
            homeTeam = "Atlético de Madrid",
            awayTeam = "Cached Club",
        )

        val matchDao = FakeMatchDao(
            initialMatches = listOf(cachedMatch),
        )

        val remoteDataSource = MatchesRemoteDataSource(
            api = FakeMatchesAtleticoApi(
                exception = IllegalStateException(
                    "Backend unavailable",
                ),
            ),
        )

        val repository = DefaultMatchesRepository(
            remoteDataSource = remoteDataSource,
            matchDao = matchDao,
        )

        val result = runCatching {
            repository.refreshMatches()
        }

        assertTrue(
            result.isFailure,
        )

        val actual = matchDao
            .observeMatches()
            .first()

        assertEquals(
            1,
            actual.size,
        )

        assertEquals(
            1,
            actual.first().id,
        )

        assertEquals(
            "Atlético de Madrid",
            actual.first().homeTeam,
        )

        assertEquals(
            "Cached Club",
            actual.first().awayTeam,
        )
    }
}

private class FakeMatchesAtleticoApi(
    private val matches: List<MatchDto> = emptyList(),
    private val exception: Exception? = null,
) : AtleticoApi {

    override suspend fun getMatches(): List<MatchDto> {
        exception?.let {
            throw it
        }

        return matches
    }

    override suspend fun getPlayers(): List<PlayerDto> {
        return emptyList()
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

    override fun observeMatchById(
        matchId: Int,
    ): Flow<MatchEntity?> {
        return matches.map { entities ->
            entities.firstOrNull { entity ->
                entity.id == matchId
            }
        }
    }

    override suspend fun getMatchById(
        matchId: Int,
    ): MatchEntity? {
        return matches.value.firstOrNull { entity ->
            entity.id == matchId
        }
    }

    override suspend fun upsertMatches(
        matches: List<MatchEntity>,
    ) {
        this.matches.value = matches
    }

    override suspend fun deleteAllMatches() {
        matches.value = emptyList()
    }

    override suspend fun getMatchCount(): Int {
        return matches.value.size
    }
}

private fun createMatchDto(
    id: Int = 1,
    competition: String = "LALIGA EA SPORTS",
    matchDay: String? = "Matchday 1",
    scheduledDate: String = "2026-08-19T19:00:00+00:00",
    kickoffAt: String? = "2026-08-19T19:00:00+00:00",
    homeTeam: String = "Atlético de Madrid",
    awayTeam: String = "Málaga CF",
    homeTeamImageUrl: String? = null,
    awayTeamImageUrl: String? = null,
    venueName: String? = "Riyadh Air Metropolitano",
    venueCity: String? = "Madrid",
    status: String = "UPCOMING",
    homeScore: Int? = null,
    awayScore: Int? = null,
): MatchDto {
    return MatchDto(
        id = id,
        competition = competition,
        matchDay = matchDay,
        scheduledDate = scheduledDate,
        kickoffAt = kickoffAt,
        homeTeam = homeTeam,
        awayTeam = awayTeam,
        homeTeamImageUrl = homeTeamImageUrl,
        awayTeamImageUrl = awayTeamImageUrl,
        venueName = venueName,
        venueCity = venueCity,
        status = status,
        homeScore = homeScore,
        awayScore = awayScore,
    )
}

private fun createMatchEntity(
    id: Int = 1,
    competition: String = "LALIGA EA SPORTS",
    matchDay: String? = "Matchday 1",
    scheduledDate: String = "2026-08-19T19:00:00+00:00",
    kickoffAt: String? = "2026-08-19T19:00:00+00:00",
    homeTeam: String = "Atlético de Madrid",
    awayTeam: String = "Málaga CF",
    homeTeamImageUrl: String? = null,
    awayTeamImageUrl: String? = null,
    venueName: String? = "Riyadh Air Metropolitano",
    venueCity: String? = "Madrid",
    status: String = "UPCOMING",
    homeScore: Int? = null,
    awayScore: Int? = null,
): MatchEntity {
    return MatchEntity(
        id = id,
        competition = competition,
        matchDay = matchDay,
        scheduledDate = scheduledDate,
        kickoffAt = kickoffAt,
        homeTeam = homeTeam,
        awayTeam = awayTeam,
        homeTeamImageUrl = homeTeamImageUrl,
        awayTeamImageUrl = awayTeamImageUrl,
        venueName = venueName,
        venueCity = venueCity,
        status = status,
        homeScore = homeScore,
        awayScore = awayScore,
    )
}