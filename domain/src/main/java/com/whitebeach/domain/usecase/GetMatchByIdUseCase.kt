package com.whitebeach.domain.usecase

import com.whitebeach.domain.model.Match
import com.whitebeach.domain.repository.MatchesRepository
import javax.inject.Inject

class GetMatchByIdUseCase @Inject constructor(
    private val repository: MatchesRepository,
) {
    suspend operator fun invoke(
        matchId: Int,
    ): Match {
        return repository.getMatchById(matchId)
            ?: throw NoSuchElementException(
                "Match not found: $matchId",
            )
    }
}