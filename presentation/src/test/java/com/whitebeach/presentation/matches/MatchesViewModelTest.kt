package com.whitebeach.presentation.matches

import com.whitebeach.domain.model.Match
import com.whitebeach.domain.model.MatchStatus
import com.whitebeach.domain.repository.MatchesRepository
import com.whitebeach.domain.usecase.GetMatchesUseCase
import com.whitebeach.presentation.test.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    fun `initial load succeeds and updates state to Success`() = runTest {
        val matches = listOf(
            Match(
                id = 1,
                competition = "LaLiga",
                homeTeam = "Atlético Madrid",
                awayTeam = "Test Club",
                date = "2026-08-10",
                time = "20:00",
                status = MatchStatus.UPCOMING,
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
                homeScore = 1,
                awayScore = 2,
            ),
        )

        val repository = FakeMatchesRepository(
            matches = matches,
        )

        val viewModel = MatchesViewModel(
            getMatchesUseCase = GetMatchesUseCase(repository),
        )

        assertEquals(
            MatchesUiState.Loading,
            viewModel.uiState.value,
        )

        advanceUntilIdle()

        assertEquals(
            MatchesUiState.Success(
                matches = matches.toUiModels(),
            ),
            viewModel.uiState.value,
        )
    }

    @Test
    fun `initial load fails and updates state to Error`() = runTest {
        val repository = FakeMatchesRepository(
            exception = IllegalStateException("Match loading failed"),
        )

        val viewModel = MatchesViewModel(
            getMatchesUseCase = GetMatchesUseCase(repository),
        )

        assertEquals(
            MatchesUiState.Loading,
            viewModel.uiState.value,
        )

        advanceUntilIdle()

        assertEquals(
            MatchesUiState.Error(
                message = "Match loading failed",
            ),
            viewModel.uiState.value,
        )
    }

    @Test
    fun `retry loads matches after previous error`() = runTest {
        val repository = RetryableMatchesRepository()

        val viewModel = MatchesViewModel(
            getMatchesUseCase = GetMatchesUseCase(repository),
        )

        advanceUntilIdle()

        assertEquals(
            MatchesUiState.Error(
                message = "First request failed",
            ),
            viewModel.uiState.value,
        )

        viewModel.loadMatches()
        advanceUntilIdle()

        assertEquals(
            MatchesUiState.Success(
                matches = repository.matches.toUiModels(),
            ),
            viewModel.uiState.value,
        )
    }
}

private class FakeMatchesRepository(
    private val matches: List<Match> = emptyList(),
    private val exception: Exception? = null,
) : MatchesRepository {

    override suspend fun getMatches(): List<Match> {
        exception?.let {
            throw it
        }

        return matches
    }

    override suspend fun getMatchById(matchId: Int): Match? {
        return matches.firstOrNull { match ->
            match.id == matchId
        }
    }
}

private class RetryableMatchesRepository : MatchesRepository {

    val matches = listOf(
        Match(
            id = 1,
            competition = "LaLiga",
            homeTeam = "Atlético Madrid",
            awayTeam = "Retry Club",
            date = "2026-08-15",
            time = "21:00",
            status = MatchStatus.UPCOMING,
            homeScore = null,
            awayScore = null,
        ),
    )

    private var requestCount = 0

    override suspend fun getMatches(): List<Match> {
        requestCount++

        if (requestCount == 1) {
            throw IllegalStateException("First request failed")
        }

        return matches
    }

    override suspend fun getMatchById(matchId: Int): Match? {
        return matches.firstOrNull { match ->
            match.id == matchId
        }
    }
}