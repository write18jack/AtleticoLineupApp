package com.whitebeach.domain.usecase

import com.whitebeach.domain.model.Player
import com.whitebeach.domain.repository.PlayersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePlayersUseCase @Inject constructor(
    private val repository: PlayersRepository,
) {
    operator fun invoke(): Flow<List<Player>> {
        return repository.observePlayers()
    }
}