package com.whitebeach.data.remote.mapper

import com.whitebeach.data.remote.dto.PlayerDto
import com.whitebeach.domain.model.Player
import com.whitebeach.domain.model.Position

fun PlayerDto.toDomain(): Player {
    return Player(
        id = id,
        name = name,
        shirtNumber = shirtNumber,
        position = position.toPosition(),
        nationality = nationality,
    )
}

fun List<PlayerDto>.toDomainModels(): List<Player> {
    return map(PlayerDto::toDomain)
}

private fun String.toPosition(): Position {
    return runCatching {
        Position.valueOf(
            uppercase(),
        )
    }.getOrDefault(
        Position.UNKNOWN,
    )
}