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
 * 後で追加するDaoTest
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
        )

        matchDao.upsertMatches(matches)

        val result = matchDao.observeMatches().first()

        assertEquals(1, result.size)
        assertEquals("Atlético Madrid", result[0].homeTeam)
    }

    @Test
    fun getMatchById_returnsCorrectMatch() = runTest {
        val match = MatchEntity(
            id = 100,
            competition = "Champions League",
            homeTeam = "Atlético Madrid",
            awayTeam = "Arsenal",
            date = "2026-10-01",
            time = "21:00",
            status = "UPCOMING",
            homeScore = null,
            awayScore = null,
        )

        matchDao.upsertMatches(
            listOf(match),
        )

        val result = matchDao.getMatchById(100)

        assertEquals(match, result)
    }

    @Test
    fun getMatchById_returnsNull_whenMatchDoesNotExist() = runTest {
        val result = matchDao.getMatchById(999)

        assertNull(result)
    }

    @Test
    fun upsertMatches_updatesExistingMatch() = runTest {
        matchDao.upsertMatches(
            listOf(
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

        matchDao.upsertMatches(
            listOf(
                MatchEntity(
                    id = 1,
                    competition = "LaLiga",
                    homeTeam = "Atlético Madrid",
                    awayTeam = "Real Madrid",
                    date = "2026-09-20",
                    time = "21:00",
                    status = "FINISHED",
                    homeScore = 2,
                    awayScore = 1,
                ),
            ),
        )

        val result = matchDao.getMatchById(1)

        assertEquals("FINISHED", result?.status)
        assertEquals(2, result?.homeScore)
        assertEquals(1, result?.awayScore)
    }

    @Test
    fun deleteAllMatches_removesAllMatches() = runTest {
        matchDao.upsertMatches(
            listOf(
                MatchEntity(
                    id = 1,
                    competition = "LaLiga",
                    homeTeam = "Atlético Madrid",
                    awayTeam = "Test Club",
                    date = "2026-09-20",
                    time = "21:00",
                    status = "UPCOMING",
                    homeScore = null,
                    awayScore = null,
                ),
            ),
        )

        matchDao.deleteAllMatches()

        val result = matchDao.observeMatches().first()

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

    private fun createMatch(
        id: Int,
    ): MatchEntity {
        return MatchEntity(
            id = id,
            competition = "LaLiga",
            homeTeam = "Atlético Madrid",
            awayTeam = "Test Club $id",
            date = "2026-09-${
                id.toString().padStart(2, '0')
            }",
            time = "21:00",
            status = "UPCOMING",
            homeScore = null,
            awayScore = null,
        )
    }

    // 並び順テスト ORDER BY date ASC, time ASC
    @Test
    fun observeMatches_returnsMatchesInDateOrder() = runTest {
        matchDao.upsertMatches(
            listOf(
                MatchEntity(
                    id = 1,
                    competition = "LaLiga",
                    homeTeam = "Atlético Madrid",
                    awayTeam = "Club B",
                    date = "2026-09-20",
                    time = "21:00",
                    status = "UPCOMING",
                    homeScore = null,
                    awayScore = null,
                ),
                MatchEntity(
                    id = 2,
                    competition = "LaLiga",
                    homeTeam = "Atlético Madrid",
                    awayTeam = "Club A",
                    date = "2026-09-10",
                    time = "20:00",
                    status = "UPCOMING",
                    homeScore = null,
                    awayScore = null,
                ),
            ),
        )

        val result = matchDao.observeMatches().first()

        assertEquals(
            listOf(2, 1),
            result.map { it.id },
        )
    }
}