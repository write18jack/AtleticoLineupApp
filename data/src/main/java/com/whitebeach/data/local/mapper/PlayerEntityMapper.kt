package com.whitebeach.data.local.mapper

import com.whitebeach.data.local.entity.PlayerEntity
import com.whitebeach.domain.model.Player
import com.whitebeach.domain.model.Position

fun PlayerEntity.toDomain(): Player {
    return Player(
        id = id,
        name = name,
        shirtNumber = shirtNumber,
        position = position.toDomainPosition(),
        nationality = nationality,
        imageUrl = imageUrl
    )
}

fun Player.toEntity(): PlayerEntity {
    return PlayerEntity(
        id = id,
        name = name,
        shirtNumber = shirtNumber,
        position = position.name,
        nationality = nationality,
        imageUrl = imageUrl
    )
}

fun List<PlayerEntity>.toDomainModels(): List<Player> {
    return map(PlayerEntity::toDomain)
}

fun List<Player>.toEntities(): List<PlayerEntity> {
    return map(Player::toEntity)
}

private fun String.toDomainPosition(): Position {
    return runCatching {
        Position.valueOf(this)
    }.getOrDefault(Position.UNKNOWN)
}