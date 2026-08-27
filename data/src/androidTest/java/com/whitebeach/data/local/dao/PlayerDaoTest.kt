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
 * DaoTest
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
            createPlayer(
                id = 1,
                name = "Jan Oblak",
                shirtNumber = 13,
                position = "GOALKEEPER",
                nationality = "SI",
            ),
            createPlayer(
                id = 2,
                name = "Antoine Griezmann",
                shirtNumber = 7,
                position = "FORWARD",
                nationality = "FR",
            ),
        )

        playerDao.upsertPlayers(players)

        val result = playerDao
            .observePlayers()
            .first()

        assertEquals(
            2,
            result.size,
        )

        assertEquals(
            "Jan Oblak",
            result[0].name,
        )

        assertEquals(
            "Antoine Griezmann",
            result[1].name,
        )
    }

    @Test
    fun getPlayerById_returnsCorrectPlayer() = runTest {
        val player = createPlayer(
            id = 10,
            name = "Test Player",
            shirtNumber = 9,
            position = "FORWARD",
            nationality = "ES",
        )

        playerDao.upsertPlayers(
            listOf(player),
        )

        val result = playerDao.getPlayerById(
            playerId = 10,
        )

        assertEquals(
            player,
            result,
        )
    }

    @Test
    fun getPlayerById_returnsNull_whenPlayerDoesNotExist() = runTest {
        val result = playerDao.getPlayerById(
            playerId = 999,
        )

        assertNull(result)
    }

    @Test
    fun observePlayerById_returnsCorrectPlayer() = runTest {
        val player = createPlayer(
            id = 10,
            name = "Jan Oblak",
        )

        playerDao.upsertPlayers(
            listOf(player),
        )

        val result = playerDao
            .observePlayerById(10)
            .first()

        assertEquals(
            player,
            result,
        )
    }

    @Test
    fun observePlayerById_returnsNull_whenPlayerDoesNotExist() = runTest {
        val result = playerDao
            .observePlayerById(999)
            .first()

        assertNull(result)
    }

    @Test
    fun upsertPlayers_updatesExistingPlayer() = runTest {
        playerDao.upsertPlayers(
            listOf(
                createPlayer(
                    id = 1,
                    name = "Old Name",
                    shirtNumber = 10,
                ),
            ),
        )

        playerDao.upsertPlayers(
            listOf(
                createPlayer(
                    id = 1,
                    name = "New Name",
                    shirtNumber = 7,
                ),
            ),
        )

        val result = playerDao.getPlayerById(
            playerId = 1,
        )

        assertEquals(
            "New Name",
            result?.name,
        )

        assertEquals(
            7,
            result?.shirtNumber,
        )
    }

    @Test
    fun upsertPlayers_updatesPlayerDetailFields() = runTest {
        playerDao.upsertPlayers(
            listOf(
                createPlayer(
                    id = 1,
                    imageUrl = null,
                    birthDate = null,
                    birthPlace = null,
                ),
            ),
        )

        playerDao.upsertPlayers(
            listOf(
                createPlayer(
                    id = 1,
                    imageUrl = "https://example.com/player.png",
                    birthDate = "1993-01-07",
                    birthPlace = "Skofja Loka",
                ),
            ),
        )

        val result = playerDao.getPlayerById(
            playerId = 1,
        )

        assertEquals(
            "https://example.com/player.png",
            result?.imageUrl,
        )

        assertEquals(
            "1993-01-07",
            result?.birthDate,
        )

        assertEquals(
            "Skofja Loka",
            result?.birthPlace,
        )
    }

    @Test
    fun nullablePlayerDetailFields_canBeStoredAsNull() = runTest {
        val player = createPlayer(
            id = 1,
            imageUrl = null,
            birthDate = null,
            birthPlace = null,
        )

        playerDao.upsertPlayers(
            listOf(player),
        )

        val result = playerDao.getPlayerById(
            playerId = 1,
        )

        assertNull(
            result?.imageUrl,
        )

        assertNull(
            result?.birthDate,
        )

        assertNull(
            result?.birthPlace,
        )
    }

    @Test
    fun deleteAllPlayers_removesAllPlayers() = runTest {
        playerDao.upsertPlayers(
            listOf(
                createPlayer(id = 1),
                createPlayer(id = 2),
            ),
        )

        playerDao.deleteAllPlayers()

        val result = playerDao
            .observePlayers()
            .first()

        assertEquals(
            emptyList<PlayerEntity>(),
            result,
        )
    }

    @Test
    fun getPlayerCount_returnsCorrectCount() = runTest {
        playerDao.upsertPlayers(
            listOf(
                createPlayer(id = 1),
                createPlayer(id = 2),
            ),
        )

        assertEquals(
            2,
            playerDao.getPlayerCount(),
        )
    }

    @Test
    fun observePlayers_returnsPlayersInPositionOrder() = runTest {
        playerDao.upsertPlayers(
            listOf(
                createPlayer(
                    id = 1,
                    name = "Forward",
                    shirtNumber = 9,
                    position = "FORWARD",
                ),
                createPlayer(
                    id = 2,
                    name = "Goalkeeper",
                    shirtNumber = 13,
                    position = "GOALKEEPER",
                ),
                createPlayer(
                    id = 3,
                    name = "Midfielder",
                    shirtNumber = 8,
                    position = "MIDFIELDER",
                ),
                createPlayer(
                    id = 4,
                    name = "Defender",
                    shirtNumber = 2,
                    position = "DEFENDER",
                ),
            ),
        )

        val result = playerDao
            .observePlayers()
            .first()

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

    @Test
    fun observePlayers_emitsUpdatedList_afterUpsert() = runTest {
        playerDao.upsertPlayers(
            listOf(
                createPlayer(
                    id = 1,
                    name = "Player 1",
                ),
            ),
        )

        assertEquals(
            1,
            playerDao
                .observePlayers()
                .first()
                .size,
        )

        playerDao.upsertPlayers(
            listOf(
                createPlayer(
                    id = 2,
                    name = "Player 2",
                    position = "FORWARD",
                ),
            ),
        )

        assertEquals(
            2,
            playerDao
                .observePlayers()
                .first()
                .size,
        )
    }

    private fun createPlayer(
        id: Int,
        name: String = "Player $id",
        shirtNumber: Int? = id,
        position: String = "MIDFIELDER",
        nationality: String = "ES",
        imageUrl: String? = null,
        birthDate: String? = null,
        birthPlace: String? = null,
    ): PlayerEntity {
        return PlayerEntity(
            id = id,
            name = name,
            shirtNumber = shirtNumber,
            position = position,
            nationality = nationality,
            imageUrl = imageUrl,
            birthDate = birthDate,
            birthPlace = birthPlace,
        )
    }
}