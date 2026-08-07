package com.whitebeach.domain.usecase

import com.whitebeach.domain.model.Match
import com.whitebeach.domain.repository.MatchesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMatchesUseCase @Inject constructor(
    private val repository: MatchesRepository,
) {
    operator fun invoke(): Flow<List<Match>> {
        return repository.observeMatches()
    }
}