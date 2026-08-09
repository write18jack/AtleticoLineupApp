package com.whitebeach.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.whitebeach.data.local.database.AtleticoDatabase
import com.whitebeach.data.local.entity.PlayerEntity
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
class PlayerDaoTest {

    private lateinit var database: AtleticoDatabase
    private lateinit var playerDao: PlayerDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AtleticoDatabase::class.java,
        )
            .allowMainThreadQueries()
            .build()

        playerDao = database.playerDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun upsertPlayers_andObservePlayers_returnsSavedPlayers() = runTest {
        val players = listOf(
            PlayerEntity(
                id = 1,
                name = "Jan Oblak",
                shirtNumber = 13,
                position = "GOALKEEPER",
                nationality = "Slovenia",
            ),
            PlayerEntity(
                id = 2,
                name = "Antoine Griezmann",
                shirtNumber = 7,
                position = "FORWARD",
                nationality = "France",
            ),
        )

        playerDao.upsertPlayers(players)

        val result = playerDao.observePlayers().first()

        assertEquals(2, result.size)
        assertEquals("Jan Oblak", result[0].name)
        assertEquals("Antoine Griezmann", result[1].name)
    }

    @Test
    fun getPlayerById_returnsCorrectPlayer() = runTest {
        val player = PlayerEntity(
            id = 10,
            name = "Test Player",
            shirtNumber = 9,
            position = "FORWARD",
            nationality = "Spain",
        )

        playerDao.upsertPlayers(
            listOf(player),
        )

        val result = playerDao.getPlayerById(10)

        assertEquals(player, result)
    }

    @Test
    fun getPlayerById_returnsNull_whenPlayerDoesNotExist() = runTest {
        val result = playerDao.getPlayerById(999)

        assertNull(result)
    }

    @Test
    fun upsertPlayers_updatesExistingPlayer() = runTest {
        playerDao.upsertPlayers(
            listOf(
                PlayerEntity(
                    id = 1,
                    name = "Old Name",
                    shirtNumber = 10,
                    position = "FORWARD",
                    nationality = "Spain",
                ),
            ),
        )

        playerDao.upsertPlayers(
            listOf(
                PlayerEntity(
                    id = 1,
                    name = "New Name",
                    shirtNumber = 7,
                    position = "FORWARD",
                    nationality = "Spain",
                ),
            ),
        )

        val result = playerDao.getPlayerById(1)

        assertEquals("New Name", result?.name)
        assertEquals(7, result?.shirtNumber)
    }

    @Test
    fun deleteAllPlayers_removesAllPlayers() = runTest {
        playerDao.upsertPlayers(
            listOf(
                PlayerEntity(
                    id = 1,
                    name = "Player 1",
                    shirtNumber = 1,
                    position = "GOALKEEPER",
                    nationality = "Spain",
                ),
            ),
        )

        playerDao.deleteAllPlayers()

        val result = playerDao.observePlayers().first()

        assertEquals(
            emptyList<PlayerEntity>(),
            result,
        )
    }

    @Test
    fun getPlayerCount_returnsCorrectCount() = runTest {
        playerDao.upsertPlayers(
            listOf(
                PlayerEntity(
                    id = 1,
                    name = "Player 1",
                    shirtNumber = 1,
                    position = "GOALKEEPER",
                    nationality = "Spain",
                ),
                PlayerEntity(
                    id = 2,
                    name = "Player 2",
                    shirtNumber = 2,
                    position = "DEFENDER",
                    nationality = "Spain",
                ),
            ),
        )

        assertEquals(
            2,
            playerDao.getPlayerCount(),
        )
    }

    // 並び順テスト
    @Test
    fun observePlayers_returnsPlayersInPositionOrder() = runTest {
        playerDao.upsertPlayers(
            listOf(
                PlayerEntity(
                    id = 1,
                    name = "Forward",
                    shirtNumber = 9,
                    position = "FORWARD",
                    nationality = "Spain",
                ),
                PlayerEntity(
                    id = 2,
                    name = "Goalkeeper",
                    shirtNumber = 13,
                    position = "GOALKEEPER",
                    nationality = "Slovenia",
                ),
                PlayerEntity(
                    id = 3,
                    name = "Midfielder",
                    shirtNumber = 8,
                    position = "MIDFIELDER",
                    nationality = "Spain",
                ),
                PlayerEntity(
                    id = 4,
                    name = "Defender",
                    shirtNumber = 2,
                    position = "DEFENDER",
                    nationality = "Spain",
                ),
            ),
        )

        val result = playerDao.observePlayers().first()

        assertEquals(
            listOf(
                "GOALKEEPER",
                "DEFENDER",
                "MIDFIELDER",
                "FORWARD",
            ),
            result.map { it.position },
        )
    }

    // Flow更新そのものをテスト
    @Test
    fun observePlayers_emitsUpdatedList_afterUpsert() = runTest {
        playerDao.upsertPlayers(
            listOf(
                PlayerEntity(
                    id = 1,
                    name = "Player 1",
                    shirtNumber = 1,
                    position = "GOALKEEPER",
                    nationality = "Spain",
                ),
            ),
        )

        assertEquals(
            1,
            playerDao.observePlayers().first().size,
        )

        playerDao.upsertPlayers(
            listOf(
                PlayerEntity(
                    id = 2,
                    name = "Player 2",
                    shirtNumber = 9,
                    position = "FORWARD",
                    nationality = "Spain",
                ),
            ),
        )

        assertEquals(
            2,
            playerDao.observePlayers().first().size,
        )
    }
}