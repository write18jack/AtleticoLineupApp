package com.whitebeach.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.whitebeach.data.local.database.AtleticoDatabase
import com.whitebeach.data.local.entity.MatchEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * DaoTest
 * → 実際のRoom SQL / Flow / Upsert
 */
@RunWith(AndroidJUnit4::class)
class MatchDaoTest {

    private lateinit var database: AtleticoDatabase
    private lateinit var matchDao: MatchDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AtleticoDatabase::class.java,
        )
            .allowMainThreadQueries()
            .build()

        matchDao = database.matchDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun upsertMatches_andObserveMatches_returnsSavedMatches() = runTest {
        val matches = listOf(
            createMatch(
                id = 1,
                homeTeam = "Atlético Madrid",
                awayTeam = "Real Madrid",
            ),
        )

        matchDao.upsertMatches(matches)

        val result = matchDao
            .observeMatches()
            .first()

        assertEquals(
            1,
            result.size,
        )

        assertEquals(
            "Atlético Madrid",
            result[0].homeTeam,
        )

        assertEquals(
            "Real Madrid",
            result[0].awayTeam,
        )

        assertEquals(
            "Matchday 1",
            result[0].matchDay,
        )
    }

    @Test
    fun getMatchById_returnsCorrectMatch() = runTest {
        val match = createMatch(
            id = 100,
            competition = "Champions League",
            matchDay = "League phase",
            homeTeam = "Atlético Madrid",
            awayTeam = "Arsenal",
            scheduledDate = "2026-10-01T19:00:00+00:00",
            kickoffAt = "2026-10-01T19:00:00+00:00",
            venueName = "Riyadh Air Metropolitano",
            venueCity = "Madrid",
        )

        matchDao.upsertMatches(
            listOf(match),
        )

        val result = matchDao.getMatchById(
            matchId = 100,
        )

        assertEquals(
            match,
            result,
        )
    }

    @Test
    fun getMatchById_returnsNull_whenMatchDoesNotExist() = runTest {
        val result = matchDao.getMatchById(
            matchId = 999,
        )

        assertNull(result)
    }

    @Test
    fun observeMatchById_returnsCorrectMatch() = runTest {
        val match = createMatch(
            id = 10,
            homeTeam = "Atlético Madrid",
            awayTeam = "Barcelona",
        )

        matchDao.upsertMatches(
            listOf(match),
        )

        val result = matchDao
            .observeMatchById(10)
            .first()

        assertEquals(
            match,
            result,
        )
    }

    @Test
    fun observeMatchById_returnsNull_whenMatchDoesNotExist() = runTest {
        val result = matchDao
            .observeMatchById(999)
            .first()

        assertNull(result)
    }

    @Test
    fun upsertMatches_updatesExistingMatch() = runTest {
        matchDao.upsertMatches(
            listOf(
                createMatch(
                    id = 1,
                    status = "UPCOMING",
                    homeScore = null,
                    awayScore = null,
                ),
            ),
        )

        matchDao.upsertMatches(
            listOf(
                createMatch(
                    id = 1,
                    status = "FINISHED",
                    homeScore = 2,
                    awayScore = 1,
                ),
            ),
        )

        val result = matchDao.getMatchById(
            matchId = 1,
        )

        assertEquals(
            "FINISHED",
            result?.status,
        )

        assertEquals(
            2,
            result?.homeScore,
        )

        assertEquals(
            1,
            result?.awayScore,
        )
    }

    @Test
    fun upsertMatches_updatesMatchDetailFields() = runTest {
        matchDao.upsertMatches(
            listOf(
                createMatch(
                    id = 1,
                    matchDay = null,
                    kickoffAt = null,
                    venueName = null,
                    venueCity = null,
                ),
            ),
        )

        matchDao.upsertMatches(
            listOf(
                createMatch(
                    id = 1,
                    matchDay = "Matchday 3",
                    kickoffAt = "2026-09-20T19:00:00+00:00",
                    venueName = "Riyadh Air Metropolitano",
                    venueCity = "Madrid",
                ),
            ),
        )

        val result = matchDao.getMatchById(
            matchId = 1,
        )

        assertEquals(
            "Matchday 3",
            result?.matchDay,
        )

        assertEquals(
            "2026-09-20T19:00:00+00:00",
            result?.kickoffAt,
        )

        assertEquals(
            "Riyadh Air Metropolitano",
            result?.venueName,
        )

        assertEquals(
            "Madrid",
            result?.venueCity,
        )
    }

    @Test
    fun kickoffAt_canBeNull_whenKickoffTimeIsNotConfirmed() = runTest {
        val match = createMatch(
            id = 1,
            scheduledDate = "2026-09-13T00:00:00+00:00",
            kickoffAt = null,
        )

        matchDao.upsertMatches(
            listOf(match),
        )

        val result = matchDao.getMatchById(
            matchId = 1,
        )

        assertEquals(
            "2026-09-13T00:00:00+00:00",
            result?.scheduledDate,
        )

        assertNull(
            result?.kickoffAt,
        )
    }

    @Test
    fun deleteAllMatches_removesAllMatches() = runTest {
        matchDao.upsertMatches(
            listOf(
                createMatch(id = 1),
                createMatch(id = 2),
            ),
        )

        matchDao.deleteAllMatches()

        val result = matchDao
            .observeMatches()
            .first()

        assertEquals(
            emptyList<MatchEntity>(),
            result,
        )
    }

    @Test
    fun getMatchCount_returnsCorrectCount() = runTest {
        matchDao.upsertMatches(
            listOf(
                createMatch(id = 1),
                createMatch(id = 2),
            ),
        )

        assertEquals(
            2,
            matchDao.getMatchCount(),
        )
    }

    @Test
    fun observeMatches_returnsMatchesInScheduledDateOrder() = runTest {
        matchDao.upsertMatches(
            listOf(
                createMatch(
                    id = 1,
                    awayTeam = "Club B",
                    scheduledDate = "2026-09-20T19:00:00+00:00",
                    kickoffAt = "2026-09-20T19:00:00+00:00",
                ),
                createMatch(
                    id = 2,
                    awayTeam = "Club A",
                    scheduledDate = "2026-09-10T18:00:00+00:00",
                    kickoffAt = "2026-09-10T18:00:00+00:00",
                ),
            ),
        )

        val result = matchDao
            .observeMatches()
            .first()

        assertEquals(
            listOf(2, 1),
            result.map { it.id },
        )
    }

    private fun createMatch(
        id: Int,
        competition: String = "LaLiga",
        matchDay: String? = "Matchday 1",
        scheduledDate: String = "2026-09-20T19:00:00+00:00",
        kickoffAt: String? = "2026-09-20T19:00:00+00:00",
        homeTeam: String = "Atlético Madrid",
        awayTeam: String = "Test Club $id",
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
}