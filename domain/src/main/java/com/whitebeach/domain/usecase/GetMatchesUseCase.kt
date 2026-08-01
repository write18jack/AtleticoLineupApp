package com.whitebeach.domain.usecase

import com.whitebeach.domain.model.Match
import com.whitebeach.domain.repository.MatchesRepository
import javax.inject.Inject

class GetMatchesUseCase @Inject constructor(
    private val repository: MatchesRepository,
) {
    suspend operator fun invoke(): List<Match> {
        return repository.getMatches()
    }
}