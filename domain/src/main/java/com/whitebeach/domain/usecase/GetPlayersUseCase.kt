package com.whitebeach.domain.usecase

import com.whitebeach.domain.model.Player
import com.whitebeach.domain.repository.PlayersRepository
import javax.inject.Inject

class GetPlayersUseCase @Inject constructor(
    private val repository: PlayersRepository,
) {
    suspend operator fun invoke(): List<Player> {
        return repository.getPlayers()
    }
}