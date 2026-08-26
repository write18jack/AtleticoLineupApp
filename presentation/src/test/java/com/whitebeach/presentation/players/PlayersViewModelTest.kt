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
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlayersViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val cachedPlayers = listOf(
        Player(
            id = 1,
            name = "Test Goalkeeper",
            shirtNumber = 13,
            position = Position.GOALKEEPER,
            nationality = "Japan",
            imageUrl = null,
            birthDate = null,
            birthPlace = null,
        ),
        Player(
            id = 2,
            name = "Test Forward",
            shirtNumber = 9,
            position = Position.FORWARD,
            nationality = "Spain",
            imageUrl = null,
            birthDate = null,
            birthPlace = null,
        ),
    )

    // cacheあり + refresh成功
    @Test
    fun `cached players and refresh success emits Success`() = runTest {
        val repository = FakePlayersRepository(
            initialPlayers = cachedPlayers,
        )

        val viewModel = createViewModel(repository)

        assertEquals(
            PlayersUiState.Loading,
            viewModel.uiState.value,
        )

        val job = collectUiState(viewModel)

        advanceUntilIdle()

        assertEquals(
            PlayersUiState.Success(
                players = cachedPlayers.toUiModels(),
                isRefreshing = false,
                refreshError = null,
            ),
            viewModel.uiState.value,
        )

        job.cancel()
    }

    // cacheあり + refresh失敗
    @Test
    fun `cached players remain visible when refresh fails`() = runTest {
        val repository = FakePlayersRepository(
            initialPlayers = cachedPlayers,
            refreshException =
                IllegalStateException("Backend unavailable"),
        )

        val viewModel = createViewModel(repository)

        val job = collectUiState(viewModel)

        advanceUntilIdle()

        assertEquals(
            PlayersUiState.Success(
                players = cachedPlayers.toUiModels(),
                isRefreshing = false,
                refreshError = "オフラインのデータを表示しています",
            ),
            viewModel.uiState.value,
        )

        job.cancel()
    }

    // cacheなし + refresh失敗
    @Test
    fun `empty cache and refresh failure emits Error`() = runTest {
        val repository = FakePlayersRepository(
            initialPlayers = emptyList(),
            refreshException =
                IllegalStateException("Backend unavailable"),
        )

        val viewModel = createViewModel(repository)

        val job = collectUiState(viewModel)

        advanceUntilIdle()

        assertEquals(
            PlayersUiState.Error(
                message = "選手情報を取得できませんでした",
            ),
            viewModel.uiState.value,
        )

        job.cancel()
    }

    // observe Flow失敗
    @Test
    fun `observe players failure emits Error`() = runTest {
        val repository = FakePlayersRepository(
            observeException =
                IllegalStateException(
                    "Player loading failed",
                ),
        )

        val viewModel = createViewModel(repository)

        val job = collectUiState(viewModel)

        advanceUntilIdle()

        assertEquals(
            PlayersUiState.Error(
                message = "Player loading failed",
            ),
            viewModel.uiState.value,
        )

        job.cancel()
    }

    // refresh中
    @Test
    fun `refreshing cached players sets isRefreshing true`() = runTest {
        val refreshGate =
            CompletableDeferred<Unit>()

        val repository = FakePlayersRepository(
            initialPlayers = cachedPlayers,
            refreshGate = refreshGate,
        )

        val viewModel = createViewModel(repository)

        val job = collectUiState(viewModel)

        advanceUntilIdle()

        val refreshingState =
            viewModel.uiState.value

        assertTrue(
            refreshingState is PlayersUiState.Success,
        )

        refreshingState as PlayersUiState.Success

        assertTrue(
            refreshingState.isRefreshing,
        )

        assertEquals(
            cachedPlayers.toUiModels(),
            refreshingState.players,
        )

        refreshGate.complete(Unit)

        advanceUntilIdle()

        val completedState =
            viewModel.uiState.value

        assertTrue(
            completedState is PlayersUiState.Success,
        )

        completedState as PlayersUiState.Success

        assertFalse(
            completedState.isRefreshing,
        )

        assertEquals(
            null,
            completedState.refreshError,
        )

        job.cancel()
    }

    // 失敗 → 再refresh成功
    @Test
    fun `manual refresh success clears previous refresh error`() = runTest {
        val repository = FakePlayersRepository(
            initialPlayers = cachedPlayers,
            refreshException =
                IllegalStateException("Backend unavailable"),
        )

        val viewModel = createViewModel(repository)

        val job = collectUiState(viewModel)

        advanceUntilIdle()

        val failedState =
            viewModel.uiState.value

        assertTrue(
            failedState is PlayersUiState.Success,
        )

        failedState as PlayersUiState.Success

        assertEquals(
            "オフラインのデータを表示しています",
            failedState.refreshError,
        )

        repository.refreshException = null

        viewModel.refreshPlayers()

        advanceUntilIdle()

        assertEquals(
            PlayersUiState.Success(
                players = cachedPlayers.toUiModels(),
                isRefreshing = false,
                refreshError = null,
            ),
            viewModel.uiState.value,
        )

        job.cancel()
    }

    // RoomをSingle Source of Truthにしている設計そのものをテストできる
    @Test
    fun `players are updated when repository emits new cache`() = runTest {
        val repository = FakePlayersRepository(
            initialPlayers = emptyList(),
        )

        val viewModel = createViewModel(repository)

        val job = collectUiState(viewModel)

        advanceUntilIdle()

        repository.updatePlayers(
            cachedPlayers,
        )

        advanceUntilIdle()

        assertEquals(
            PlayersUiState.Success(
                players = cachedPlayers.toUiModels(),
                isRefreshing = false,
                refreshError = null,
            ),
            viewModel.uiState.value,
        )

        job.cancel()
    }

    private fun createViewModel(
        repository: PlayersRepository,
    ): PlayersViewModel {
        return PlayersViewModel(
            observePlayersUseCase =
                ObservePlayersUseCase(repository),
            refreshPlayersUseCase =
                RefreshPlayersUseCase(repository),
        )
    }

    private fun kotlinx.coroutines.test.TestScope.collectUiState(
        viewModel: PlayersViewModel,
    ) = backgroundScope.launch(
        UnconfinedTestDispatcher(testScheduler),
    ) {
        viewModel.uiState.collect()
    }
}

private class FakePlayersRepository(
    initialPlayers: List<Player> = emptyList(),
    private val observeException: Exception? = null,
    var refreshException: Exception? = null,
    private val refreshGate:
    CompletableDeferred<Unit>? = null,
) : PlayersRepository {

    private val playersFlow = MutableStateFlow(initialPlayers)

    fun updatePlayers(
        players: List<Player>,
    ) {
        playersFlow.value = players
    }

    override fun observePlayers(): Flow<List<Player>> {
        observeException?.let { exception ->
            return flow {
                throw exception
            }
        }

        return playersFlow
    }

    override suspend fun getPlayerById(
        playerId: Int,
    ): Player? {
        return playersFlow.value
            .firstOrNull { player ->
                player.id == playerId
            }
    }

    override suspend fun refreshPlayers() {
        refreshGate?.await()

        refreshException?.let {
            throw it
        }
    }
}