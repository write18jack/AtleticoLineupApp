package com.whitebeach.data.repository

import com.whitebeach.data.local.dao.PlayerDao
import com.whitebeach.data.local.entity.PlayerEntity
import com.whitebeach.data.remote.api.AtleticoApi
import com.whitebeach.data.remote.datasource.PlayersRemoteDataSource
import com.whitebeach.data.remote.dto.MatchDto
import com.whitebeach.data.remote.dto.PlayerDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * RepositoryTest
 * → Remote → Mapper → DAO
 */
class DefaultPlayersRepositoryTest {

    // Room → Domain変換
    @Test
    fun `observePlayers converts entities to domain models`() = runTest {
        val dao = FakePlayerDao(
            initialPlayers = listOf(
                createPlayerEntity(),
            ),
        )

        val repository = DefaultPlayersRepository(
            remoteDataSource = PlayersRemoteDataSource(
                api = FakePlayersAtleticoApi(),
            ),
            playerDao = dao,
        )

        val result = repository
            .observePlayers()
            .first()

        assertEquals(1, result.size)
        assertEquals(1, result[0].id)
        assertEquals("Jan Oblak", result[0].name)
        assertEquals(13, result[0].shirtNumber)
    }

    // ID検索
    @Test
    fun `getPlayerById returns mapped domain player`() = runTest {
        val dao = FakePlayerDao(
            initialPlayers = listOf(
                createPlayerEntity(),
            ),
        )

        val repository = DefaultPlayersRepository(
            remoteDataSource = PlayersRemoteDataSource(
                api = FakePlayersAtleticoApi(),
            ),
            playerDao = dao,
        )

        val result = repository.getPlayerById(
            playerId = 1,
        )

        assertEquals(
            "Jan Oblak",
            result?.name,
        )

        assertEquals(
            13,
            result?.shirtNumber,
        )
    }

    // 存在しないID
    @Test
    fun `getPlayerById returns null when player does not exist`() = runTest {
        val dao = FakePlayerDao()

        val repository = DefaultPlayersRepository(
            remoteDataSource = PlayersRemoteDataSource(
                api = FakePlayersAtleticoApi(),
            ),
            playerDao = dao,
        )

        val result = repository.getPlayerById(
            playerId = 999,
        )

        assertNull(result)
    }

    // Remote成功 → Room保存
    @Test
    fun `refreshPlayers fetches remote players and saves them to dao`() = runTest {
        val dao = FakePlayerDao()

        val api = FakePlayersAtleticoApi(
            players = listOf(
                createPlayerDto(
                    id = 10,
                    name = "Remote Player",
                    shirtNumber = 7,
                    position = "FORWARD",
                    nationality = "Spain",
                ),
            ),
        )

        val repository = DefaultPlayersRepository(
            remoteDataSource = PlayersRemoteDataSource(
                api = api,
            ),
            playerDao = dao,
        )

        repository.refreshPlayers()

        val savedPlayers = dao
            .observePlayers()
            .first()

        assertEquals(
            1,
            savedPlayers.size,
        )

        assertEquals(
            createPlayerEntity(
                id = 10,
                name = "Remote Player",
                shirtNumber = 7,
                position = "FORWARD",
                nationality = "Spain",
            ),
            savedPlayers.first(),
        )
    }

    // Remote失敗 → 例外伝播
    @Test
    fun `refreshPlayers propagates remote exception`() = runTest {
        val repository = DefaultPlayersRepository(
            remoteDataSource = PlayersRemoteDataSource(
                api = FakePlayersAtleticoApi(
                    exception = IllegalStateException(
                        "Backend unavailable",
                    ),
                ),
            ),
            playerDao = FakePlayerDao(),
        )

        val result = runCatching {
            repository.refreshPlayers()
        }

        assertTrue(
            result.exceptionOrNull() is IllegalStateException,
        )

        assertEquals(
            "Backend unavailable",
            result.exceptionOrNull()?.message,
        )
    }

    // Remote失敗 → キャッシュ維持
    @Test
    fun `refreshPlayers does not overwrite cached players when remote fails`() = runTest {
        val cachedPlayer = createPlayerEntity(
            id = 1,
            name = "Cached Player",
        )

        val dao = FakePlayerDao(
            initialPlayers = listOf(cachedPlayer),
        )

        val repository = DefaultPlayersRepository(
            remoteDataSource = PlayersRemoteDataSource(
                api = FakePlayersAtleticoApi(
                    exception = IllegalStateException(
                        "Backend unavailable",
                    ),
                ),
            ),
            playerDao = dao,
        )

        val result = runCatching {
            repository.refreshPlayers()
        }

        assertTrue(
            result.isFailure,
        )

        val players = dao
            .observePlayers()
            .first()

        assertEquals(
            1,
            players.size,
        )

        assertEquals(
            "Cached Player",
            players.first().name,
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
        return players.map { entities ->
            entities.firstOrNull { entity ->
                entity.id == playerId
            }
        }
    }

    override suspend fun getPlayerById(
        playerId: Int,
    ): PlayerEntity? {
        return players.value.firstOrNull { entity ->
            entity.id == playerId
        }
    }

    override suspend fun upsertPlayers(
        players: List<PlayerEntity>,
    ) {
        val current =
            this.players.value
                .associateBy { it.id }
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

private class FakePlayersAtleticoApi(
    private val players: List<PlayerDto> = emptyList(),
    private val exception: Exception? = null,
) : AtleticoApi {

    override suspend fun getPlayers(): List<PlayerDto> {
        exception?.let {
            throw it
        }

        return players
    }

    override suspend fun getMatches(): List<MatchDto> {
        return emptyList()
    }
}

private fun createPlayerDto(
    id: Int = 1,
    name: String = "Jan Oblak",
    shirtNumber: Int? = 13,
    position: String = "GOALKEEPER",
    nationality: String = "Slovenia",
    imageUrl: String? = "",
    birthDate: String? = "",
    birthPlace: String? = "",
): PlayerDto {
    return PlayerDto(
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

private fun createPlayerEntity(
    id: Int = 1,
    name: String = "Jan Oblak",
    shirtNumber: Int? = 13,
    position: String = "GOALKEEPER",
    nationality: String = "Slovenia",
    imageUrl: String? = "",
    birthDate: String? = "",
    birthPlace: String? = "",
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