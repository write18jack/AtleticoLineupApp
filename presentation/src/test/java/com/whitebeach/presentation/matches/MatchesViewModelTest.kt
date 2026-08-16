package com.whitebeach.presentation.matches

import com.whitebeach.domain.model.Match
import com.whitebeach.domain.model.MatchStatus
import com.whitebeach.domain.repository.MatchesRepository
import com.whitebeach.domain.usecase.ObserveMatchesUseCase
import com.whitebeach.domain.usecase.RefreshMatchesUseCase
import com.whitebeach.presentation.test.MainDispatcherRule
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

    @Test
    fun `observe matches emits Success`() = runTest {
        val matches = listOf(
            Match(
                id = 1,
                competition = "LaLiga",
                homeTeam = "Atlético Madrid",
                awayTeam = "Test Club",
                date = "2026-08-10",
                time = "20:00",
                status = MatchStatus.UPCOMING,
                homeTeamImageUrl = "",
                awayTeamImageUrl = "",
                homeScore = null,
                awayScore = null,
            ),
            Match(
                id = 2,
                competition = "LaLiga",
                homeTeam = "Test Club",
                awayTeam = "Atlético Madrid",
                date = "2026-08-01",
                time = "Finished",
                status = MatchStatus.FINISHED,
                homeTeamImageUrl = "",
                awayTeamImageUrl = "",
                homeScore = 1,
                awayScore = 2,
            ),
        )

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
            ),
            viewModel.uiState.value,
        )

        job.cancel()
    }

    @Test
    fun `observe matches emits Error`() = runTest {
        val repository = FakeMatchesRepository(
            exception = IllegalStateException("Match loading failed"),
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
            MatchesUiState.Error(
                message = "Match loading failed",
            ),
            viewModel.uiState.value,
        )

        job.cancel()
    }
}

private class FakeMatchesRepository(
    private val matches: List<Match> = emptyList(),
    private val exception: Exception? = null,
) : MatchesRepository {

    override fun observeMatches(): Flow<List<Match>> {
        return flow {
            exception?.let {
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
        // 今は何もしない
    }
}
