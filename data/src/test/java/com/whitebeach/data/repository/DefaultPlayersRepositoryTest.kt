package com.whitebeach.data.repository

import com.whitebeach.data.local.dao.PlayerDao
import com.whitebeach.data.local.entity.PlayerEntity
import com.whitebeach.data.remote.api.AtleticoApi
import com.whitebeach.data.remote.datasource.PlayersRemoteDataSource
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
class DefaultPlayersRepositoryTest {

    @Test
    fun `observePlayers converts entities to domain models`() = runTest {
        val dao = FakePlayerDao(
            initialPlayers = listOf(
                PlayerEntity(
                    id = 1,
                    name = "Jan Oblak",
                    shirtNumber = 13,
                    position = "GOALKEEPER",
                    nationality = "Slovenia",
                    imageUrl = ""
                ),
            ),
        )

        val repository = DefaultPlayersRepository(
            remoteDataSource = PlayersRemoteDataSource(
                api = FakeAtleticoApi(),
            ),
            playerDao = dao,
        )

        val result = repository.observePlayers().first()

        assertEquals(1, result.size)
        assertEquals(1, result[0].id)
        assertEquals("Jan Oblak", result[0].name)
        assertEquals(13, result[0].shirtNumber)
    }

    @Test
    fun `getPlayerById returns mapped domain player`() = runTest {
        val dao = FakePlayerDao(
            initialPlayers = listOf(
                PlayerEntity(
                    id = 1,
                    name = "Jan Oblak",
                    shirtNumber = 13,
                    position = "GOALKEEPER",
                    nationality = "Slovenia",
                    imageUrl = ""
                ),
            ),
        )

        val repository = DefaultPlayersRepository(
            remoteDataSource = PlayersRemoteDataSource(
                api = FakeAtleticoApi(),
            ),
            playerDao = dao,
        )

        val result = repository.getPlayerById(1)

        assertEquals("Jan Oblak", result?.name)
        assertEquals(13, result?.shirtNumber)
    }

    @Test
    fun `refreshPlayers fetches remote players and saves them to dao`() = runTest {
        val dao = FakePlayerDao()

        val api = FakeAtleticoApi(
            players = listOf(
                PlayerDto(
                    id = 10,
                    name = "Remote Player",
                    shirtNumber = 7,
                    position = "FORWARD",
                    nationality = "Spain",
                    imageUrl = ""
                ),
            ),
        )

        val repository = DefaultPlayersRepository(
            remoteDataSource = PlayersRemoteDataSource(api),
            playerDao = dao,
        )

        repository.refreshPlayers()

        val savedPlayers = dao.observePlayers().first()

        assertEquals(1, savedPlayers.size)

        assertEquals(
            PlayerEntity(
                id = 10,
                name = "Remote Player",
                shirtNumber = 7,
                position = "FORWARD",
                nationality = "Spain",
                imageUrl = ""
            ),
            savedPlayers.first(),
        )
    }
}

private class FakePlayerDao(
    initialPlayers: List<PlayerEntity> = emptyList(),
) : PlayerDao {

    private val players =
        MutableStateFlow(initialPlayers)

    override fun observePlayers(): Flow<List<PlayerEntity>> {
        return players
    }

    override fun observePlayerById(
        playerId: Int,
    ): Flow<PlayerEntity?> {
        return MutableStateFlow(
            players.value.firstOrNull {
                it.id == playerId
            },
        )
    }

    override suspend fun getPlayerById(
        playerId: Int,
    ): PlayerEntity? {
        return players.value.firstOrNull {
            it.id == playerId
        }
    }

    override suspend fun upsertPlayers(
        players: List<PlayerEntity>,
    ) {
        val current =
            this.players.value.associateBy { it.id }
                .toMutableMap()

        players.forEach { player ->
            current[player.id] = player
        }

        this.players.value =
            current.values.toList()
    }

    override suspend fun deleteAllPlayers() {
        players.value = emptyList()
    }

    override suspend fun getPlayerCount(): Int {
        return players.value.size
    }
}

private class FakeAtleticoApi(
    private val players: List<PlayerDto> = emptyList(),
) : AtleticoApi {

    override suspend fun getPlayers(): List<PlayerDto> {
        return players
    }

    override suspend fun getMatches() =
        emptyList<com.whitebeach.data.remote.dto.MatchDto>()
}