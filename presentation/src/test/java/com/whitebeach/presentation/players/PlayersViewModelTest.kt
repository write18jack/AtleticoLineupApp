package com.whitebeach.presentation.players

import com.whitebeach.domain.model.Player
import com.whitebeach.domain.model.Position
import com.whitebeach.domain.repository.PlayersRepository
import com.whitebeach.domain.usecase.GetPlayersUseCase
import com.whitebeach.presentation.players.list.PlayersUiState
import com.whitebeach.presentation.players.list.PlayersViewModel
import com.whitebeach.presentation.players.list.toUiModels
import com.whitebeach.presentation.test.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlayersViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `initial load succeeds and updates state to Success`() = runTest {
        val players = listOf(
            Player(
                id = 1,
                name = "Test Goalkeeper",
                shirtNumber = 13,
                position = Position.GOALKEEPER,
                nationality = "Japan",
            ),
            Player(
                id = 2,
                name = "Test Forward",
                shirtNumber = 9,
                position = Position.FORWARD,
                nationality = "Spain",
            ),
        )

        val repository = FakePlayersRepository(
            players = players,
        )

        val viewModel = PlayersViewModel(
            getPlayersUseCase = GetPlayersUseCase(repository),
        )

        assertEquals(
            PlayersUiState.Loading,
            viewModel.uiState.value,
        )

        advanceUntilIdle()

        assertEquals(
            PlayersUiState.Success(
                players = players.toUiModels(),
            ),
            viewModel.uiState.value,
        )
    }

    @Test
    fun `initial load fails and updates state to Error`() = runTest {
        val repository = FakePlayersRepository(
            exception = IllegalStateException("Player loading failed"),
        )

        val viewModel = PlayersViewModel(
            getPlayersUseCase = GetPlayersUseCase(repository),
        )

        assertEquals(
            PlayersUiState.Loading,
            viewModel.uiState.value,
        )

        advanceUntilIdle()

        assertEquals(
            PlayersUiState.Error(
                message = "Player loading failed",
            ),
            viewModel.uiState.value,
        )
    }

    @Test
    fun `retry loads players after previous error`() = runTest {
        val repository = RetryablePlayersRepository()

        val viewModel = PlayersViewModel(
            getPlayersUseCase = GetPlayersUseCase(repository),
        )

        advanceUntilIdle()

        assertEquals(
            PlayersUiState.Error(
                message = "First request failed",
            ),
            viewModel.uiState.value,
        )

        viewModel.loadPlayers()
        advanceUntilIdle()

        assertEquals(
            PlayersUiState.Success(
                players = repository.players.toUiModels(),
            ),
            viewModel.uiState.value,
        )
    }
}

private class FakePlayersRepository(
    private val players: List<Player> = emptyList(),
    private val exception: Exception? = null,
) : PlayersRepository {

    override suspend fun getPlayers(): List<Player> {
        exception?.let {
            throw it
        }

        return players
    }

    override suspend fun getPlayerById(playerId: Int): Player? {
        return players.firstOrNull { player ->
            player.id == playerId
        }
    }
}

private class RetryablePlayersRepository : PlayersRepository {

    val players = listOf(
        Player(
            id = 1,
            name = "Retry Player",
            shirtNumber = 10,
            position = Position.FORWARD,
            nationality = "Japan",
        ),
    )

    private var requestCount = 0

    override suspend fun getPlayers(): List<Player> {
        requestCount++

        if (requestCount == 1) {
            throw IllegalStateException("First request failed")
        }

        return players
    }

    override suspend fun getPlayerById(playerId: Int): Player? {
        return players.firstOrNull { player ->
            player.id == playerId
        }
    }
}