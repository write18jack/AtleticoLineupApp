package com.whitebeach.data.remote.datasource

import com.whitebeach.data.remote.api.AtleticoApi
import com.whitebeach.data.remote.dto.MatchDto
import com.whitebeach.data.remote.dto.PlayerDto
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PlayersRemoteDataSourceTest {

    @Test
    fun `getPlayers returns players from api`() = runTest {
        val expected = listOf(
            createPlayerDto(
                id = 1,
                name = "Jan Oblak",
                shirtNumber = 13,
                position = "GOALKEEPER",
                nationality = "SI",
                imageUrl = "https://example.com/oblak.png",
                birthDate = "1993-01-07",
                birthPlace = "Skofja Loka",
            ),
            createPlayerDto(
                id = 2,
                name = "Julian Alvarez",
                shirtNumber = 19,
                position = "FORWARD",
                nationality = "AR",
            ),
        )

        val api = FakePlayersAtleticoApi(
            players = expected,
        )

        val remoteDataSource = PlayersRemoteDataSource(
            api = api,
        )

        val actual = remoteDataSource.getPlayers()

        assertEquals(
            expected,
            actual,
        )
    }

    @Test
    fun `getPlayers preserves nullable fields`() = runTest {
        val api = FakePlayersAtleticoApi(
            players = listOf(
                createPlayerDto(
                    id = 1,
                    shirtNumber = null,
                    imageUrl = null,
                    birthDate = null,
                    birthPlace = null,
                ),
            ),
        )

        val remoteDataSource = PlayersRemoteDataSource(
            api = api,
        )

        val actual = remoteDataSource
            .getPlayers()
            .first()

        assertNull(
            actual.shirtNumber,
        )

        assertNull(
            actual.imageUrl,
        )

        assertNull(
            actual.birthDate,
        )

        assertNull(
            actual.birthPlace,
        )
    }

    @Test
    fun `getPlayers propagates api exception`() = runTest {
        val remoteDataSource = PlayersRemoteDataSource(
            api = FakePlayersAtleticoApi(
                exception = IllegalStateException(
                    "Backend unavailable",
                ),
            ),
        )

        val result = runCatching {
            remoteDataSource.getPlayers()
        }

        assertTrue(
            result.exceptionOrNull() is IllegalStateException,
        )

        assertEquals(
            "Backend unavailable",
            result.exceptionOrNull()?.message,
        )
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
    name: String = "Test Player",
    shirtNumber: Int? = 10,
    position: String = "MIDFIELDER",
    nationality: String = "ES",
    imageUrl: String? = null,
    birthDate: String? = null,
    birthPlace: String? = null,
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