package com.whitebeach.presentation.matches

import com.whitebeach.domain.model.Match
import com.whitebeach.domain.model.MatchStatus
import com.whitebeach.domain.repository.MatchesRepository
import com.whitebeach.domain.usecase.ObserveMatchesUseCase
import com.whitebeach.domain.usecase.RefreshMatchesUseCase
import com.whitebeach.presentation.matches.list.MatchesUiState
import com.whitebeach.presentation.matches.list.MatchesViewModel
import com.whitebeach.presentation.matches.list.toUiModels
import com.whitebeach.presentation.test.MainDispatcherRule
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MatchesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // キャッシュあり + refresh成功
    @Test
    fun `cached matches and refresh success emits Success`() = runTest {
        val matches = listOf(cachedMatch)

        val repository = FakeMatchesRepository(
            matches = matches,
        )

        val viewModel = MatchesViewModel(
            observeMatchesUseCase = ObserveMatchesUseCase(repository),
            refreshMatchesUseCase = RefreshMatchesUseCase(repository),
        )

        assertEquals(
            MatchesUiState.Loading,
            viewModel.uiState.value,
        )

        val job = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler),
        ) {
            viewModel.uiState.collect()
        }

        advanceUntilIdle()

        assertEquals(
            MatchesUiState.Success(
                matches = matches.toUiModels(),
                isRefreshing = false,
                refreshError = null,
            ),
            viewModel.uiState.value,
        )

        job.cancel()
    }

    // キャッシュあり + Backend失敗
    // 今のOffline-first設計を保証する重要なテスト
    @Test
    fun `cached matches remain visible when refresh fails`() = runTest {
        val matches = listOf(cachedMatch)

        val repository = FakeMatchesRepository(
            matches = matches,
            refreshException =
                IllegalStateException("Backend unavailable"),
        )

        val viewModel = MatchesViewModel(
            observeMatchesUseCase = ObserveMatchesUseCase(repository),
            refreshMatchesUseCase = RefreshMatchesUseCase(repository),
        )

        val job = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler),
        ) {
            viewModel.uiState.collect()
        }

        advanceUntilIdle()

        assertEquals(
            MatchesUiState.Success(
                matches = matches.toUiModels(),
                isRefreshing = false,
                refreshError = "オフラインのデータを表示しています",
            ),
            viewModel.uiState.value,
        )

        job.cancel()
    }

    // Room空 + Backend失敗
    @Test
    fun `empty cache and refresh failure emits Error`() = runTest {
        val repository = FakeMatchesRepository(
            matches = emptyList(),
            refreshException =
                IllegalStateException("Backend unavailable"),
        )

        val viewModel = MatchesViewModel(
            observeMatchesUseCase = ObserveMatchesUseCase(repository),
            refreshMatchesUseCase = RefreshMatchesUseCase(repository),
        )

        val job = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler),
        ) {
            viewModel.uiState.collect()
        }

        advanceUntilIdle()

        assertEquals(
            MatchesUiState.Error(
                message = "試合情報を取得できませんでした",
            ),
            viewModel.uiState.value,
        )

        job.cancel()
    }

    // Room Flow自体が失敗
    @Test
    fun `observe matches failure emits Error`() = runTest {
        val repository = FakeMatchesRepository(
            observeException = IllegalStateException("Match loading failed"),
        )

        val viewModel = MatchesViewModel(
            observeMatchesUseCase = ObserveMatchesUseCase(repository),
            refreshMatchesUseCase = RefreshMatchesUseCase(repository),
        )

        val job = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler),
        ) {
            viewModel.uiState.collect()
        }

        advanceUntilIdle()

        assertEquals(
            MatchesUiState.Error(
                message = "Match loading failed",
            ),
            viewModel.uiState.value,
        )

        job.cancel()
    }

    // isRefreshing をテスト
    @Test
    fun `refreshing cached matches sets isRefreshing true`() = runTest {
        val refreshGate =
            CompletableDeferred<Unit>()

        val repository = FakeMatchesRepository(
            matches = listOf(cachedMatch),
            refreshGate = refreshGate,
        )

        val viewModel = MatchesViewModel(
            observeMatchesUseCase = ObserveMatchesUseCase(repository),
            refreshMatchesUseCase = RefreshMatchesUseCase(repository),
        )

        val job = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler),
        ) {
            viewModel.uiState.collect()
        }

        advanceUntilIdle()

        val refreshingState =
            viewModel.uiState.value

        assertEquals(
            true,
            (refreshingState as MatchesUiState.Success)
                .isRefreshing,
        )

        refreshGate.complete(Unit)

        advanceUntilIdle()

        assertEquals(
            false,
            (viewModel.uiState.value as MatchesUiState.Success)
                .isRefreshing,
        )

        job.cancel()
    }
}

private val cachedMatch = Match(
    id = 1,
    competition = "LALIGA EA SPORTS",
    matchDay = "Matchday 1",
    scheduledDate = "2026-08-19T19:00:00+00:00",
    kickoffAt = "2026-08-19T19:00:00+00:00",
    homeTeam = "Atlético de Madrid",
    awayTeam = "Málaga CF",
    homeTeamImageUrl = "",
    awayTeamImageUrl = "",
    venueName = "Riyadh Air Metropolitano",
    venueCity = "Madrid",
    status = MatchStatus.UPCOMING,
    homeScore = null,
    awayScore = null,
)

private class FakeMatchesRepository(
    private val matches: List<Match> = emptyList(),
    private val observeException: Exception? = null,
    private val refreshException: Exception? = null,
    private val refreshGate: CompletableDeferred<Unit>? = null,
) : MatchesRepository {

    override fun observeMatches(): Flow<List<Match>> {
        return flow {
            observeException?.let {
                throw it
            }

            emit(matches)
        }
    }

    override suspend fun getMatchById(
        matchId: Int,
    ): Match? {
        return matches.firstOrNull { match ->
            match.id == matchId
        }
    }

    override suspend fun refreshMatches() {
        refreshGate?.await()

        refreshException?.let {
            throw it
        }
    }
}
