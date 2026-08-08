package com.whitebeach.domain.usecase

import com.whitebeach.domain.repository.MatchesRepository
import javax.inject.Inject

class RefreshMatchesUseCase @Inject constructor(
    private val repository: MatchesRepository,
) {

    suspend operator fun invoke() {
        repository.refreshMatches()
    }
}