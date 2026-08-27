package com.whitebeach.presentation.players

import com.whitebeach.domain.model.Player
import com.whitebeach.domain.model.Position
import com.whitebeach.presentation.players.list.toUiModel
import com.whitebeach.presentation.players.list.toUiModels
import com.whitebeach.presentation.players.model.PlayerPositionUi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class PlayerMapperTest {

    @Test
    fun `player is mapped to ui model correctly`() {
        val player = createPlayer(
            id = 1,
            name = "Jan Oblak",
            shirtNumber = 13,
            position = Position.GOALKEEPER,
            nationality = "SI",
            imageUrl = "https://example.com/oblak.png",
            birthDate = "1993-01-07",
            birthPlace = "Skofja Loka",
        )

        val actual = player.toUiModel()

        assertEquals(
            1,
            actual.id,
        )

        assertEquals(
            "Jan Oblak",
            actual.name,
        )

        assertEquals(
            13,
            actual.shirtNumber,
        )

        assertEquals(
            PlayerPositionUi.GOALKEEPER,
            actual.position,
        )

        assertEquals(
            "Slovenia",
            actual.nationality,
        )

        assertEquals(
            "https://example.com/oblak.png",
            actual.imageUrl,
        )

        assertEquals(
            "1993-01-07",
            actual.birthDate,
        )

        assertEquals(
            "Skofja Loka",
            actual.birthPlace,
        )
    }

    @Test
    fun `goalkeeper position is mapped to GOALKEEPER`() {
        val player = createPlayer(
            position = Position.GOALKEEPER,
        )

        val actual = player.toUiModel()

        assertEquals(
            PlayerPositionUi.GOALKEEPER,
            actual.position,
        )
    }

    @Test
    fun `defender position is mapped to DEFENDER`() {
        val player = createPlayer(
            position = Position.DEFENDER,
        )

        val actual = player.toUiModel()

        assertEquals(
            PlayerPositionUi.DEFENDER,
            actual.position,
        )
    }

    @Test
    fun `midfielder position is mapped to MIDFIELDER`() {
        val player = createPlayer(
            position = Position.MIDFIELDER,
        )

        val actual = player.toUiModel()

        assertEquals(
            PlayerPositionUi.MIDFIELDER,
            actual.position,
        )
    }

    @Test
    fun `forward position is mapped to FORWARD`() {
        val player = createPlayer(
            position = Position.FORWARD,
        )

        val actual = player.toUiModel()

        assertEquals(
            PlayerPositionUi.FORWARD,
            actual.position,
        )
    }

    @Test
    fun `unknown position is mapped to UNKNOWN`() {
        val player = createPlayer(
            position = Position.UNKNOWN,
        )

        val actual = player.toUiModel()

        assertEquals(
            PlayerPositionUi.UNKNOWN,
            actual.position,
        )
    }

    @Test
    fun `SI nationality is mapped to Slovenia`() {
        val player = createPlayer(
            nationality = "SI",
        )

        val actual = player.toUiModel()

        assertEquals(
            "Slovenia",
            actual.nationality,
        )
    }

    @Test
    fun `ES nationality is mapped to Spain`() {
        val player = createPlayer(
            nationality = "ES",
        )

        val actual = player.toUiModel()

        assertEquals(
            "Spain",
            actual.nationality,
        )
    }

    @Test
    fun `unknown nationality keeps original value`() {
        val player = createPlayer(
            nationality = "XX",
        )

        val actual = player.toUiModel()

        assertEquals(
            "XX",
            actual.nationality,
        )
    }

    @Test
    fun `nullable player fields remain null`() {
        val player = createPlayer(
            shirtNumber = null,
            imageUrl = null,
            birthDate = null,
            birthPlace = null,
        )

        val actual = player.toUiModel()

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
    fun `list of players is mapped to ui models`() {
        val players = listOf(
            createPlayer(
                id = 1,
                name = "Jan Oblak",
                position = Position.GOALKEEPER,
            ),
            createPlayer(
                id = 2,
                name = "Julián Álvarez",
                position = Position.FORWARD,
            ),
        )

        val actual = players.toUiModels()

        assertEquals(
            2,
            actual.size,
        )

        assertEquals(
            1,
            actual[0].id,
        )

        assertEquals(
            "Jan Oblak",
            actual[0].name,
        )

        assertEquals(
            PlayerPositionUi.GOALKEEPER,
            actual[0].position,
        )

        assertEquals(
            2,
            actual[1].id,
        )

        assertEquals(
            "Julián Álvarez",
            actual[1].name,
        )

        assertEquals(
            PlayerPositionUi.FORWARD,
            actual[1].position,
        )
    }

    private fun createPlayer(
        id: Int = 1,
        name: String = "Test Player",
        shirtNumber: Int? = 10,
        position: Position = Position.MIDFIELDER,
        nationality: String = "ES",
        imageUrl: String? = null,
        birthDate: String? = null,
        birthPlace: String? = null,
    ): Player {
        return Player(
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