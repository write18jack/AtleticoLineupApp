package com.whitebeach.presentation.players

import com.whitebeach.domain.model.Player
import com.whitebeach.domain.model.Position
import com.whitebeach.domain.repository.PlayersRepository
import com.whitebeach.domain.usecase.ObservePlayersUseCase
import com.whitebeach.domain.usecase.RefreshPlayersUseCase
import com.whitebeach.presentation.players.list.PlayersUiState
import com.whitebeach.presentation.players.list.PlayersViewModel
import com.whitebeach.presentation.players.list.toUiModels
import com.whitebeach.presentation.test.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
    fun `observe players emits Success`() = runTest {
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
            initialPlayers = players,
        )

        val viewModel = PlayersViewModel(
            observePlayersUseCase = ObservePlayersUseCase(repository),
            refreshPlayersUseCase = RefreshPlayersUseCase(repository),
        )

        assertEquals(
            PlayersUiState.Loading,
            viewModel.uiState.value,
        )

        val job = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler),
        ) {
            viewModel.uiState.collect()
        }

        advanceUntilIdle()

        assertEquals(
            PlayersUiState.Success(
                players = players.toUiModels(),
            ),
            viewModel.uiState.value,
        )

        job.cancel()
    }

    @Test
    fun `observe players emits Error`() = runTest {
        val repository = FakePlayersRepository(
            observeException = IllegalStateException(
                "Player loading failed",
            ),
        )

        val viewModel = PlayersViewModel(
            observePlayersUseCase = ObservePlayersUseCase(repository),
            refreshPlayersUseCase = RefreshPlayersUseCase(repository),
        )

        val job = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler),
        ) {
            viewModel.uiState.collect()
        }

        advanceUntilIdle()

        assertEquals(
            PlayersUiState.Error(
                message = "Player loading failed",
            ),
            viewModel.uiState.value,
        )

        job.cancel()
    }
}

private class FakePlayersRepository(
    initialPlayers: List<Player> = emptyList(),
    private val observeException: Exception? = null,
    private val refreshException: Exception? = null,
) : PlayersRepository {

    private val playersFlow =
        MutableStateFlow(initialPlayers)

    override fun observePlayers(): Flow<List<Player>> {
        observeException?.let { exception ->
            return kotlinx.coroutines.flow.flow {
                throw exception
            }
        }

        return playersFlow
    }

    override suspend fun getPlayerById(
        playerId: Int,
    ): Player? {
        return playersFlow.value.firstOrNull { player ->
            player.id == playerId
        }
    }

    override suspend fun refreshPlayers() {
        refreshException?.let {
            throw it
        }
    }
}