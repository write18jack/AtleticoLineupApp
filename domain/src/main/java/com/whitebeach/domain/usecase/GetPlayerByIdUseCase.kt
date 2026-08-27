package com.whitebeach.domain.usecase

import com.whitebeach.domain.model.Player
import com.whitebeach.domain.repository.PlayersRepository
import javax.inject.Inject

class GetPlayerByIdUseCase @Inject constructor(
    private val repository: PlayersRepository,
) {
    suspend operator fun invoke(
        playerId: Int,
    ): Player {
        return repository.getPlayerById(playerId)
            ?: throw NoSuchElementException(
                "Player not found: $playerId",
            )
    }
}