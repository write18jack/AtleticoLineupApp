package com.whitebeach.domain.usecase

import com.whitebeach.domain.repository.PlayersRepository
import javax.inject.Inject

/**
 * Remote API取得 → Room更新
 */
class RefreshPlayersUseCase @Inject constructor(
    private val repository: PlayersRepository,
) {

    suspend operator fun invoke() {
        repository.refreshPlayers()
    }
}