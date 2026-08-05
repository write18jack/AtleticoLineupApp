package com.whitebeach.presentation.players.list

import com.whitebeach.domain.model.Player
import com.whitebeach.domain.model.Position
import com.whitebeach.presentation.players.model.PlayerPositionUi
import com.whitebeach.presentation.players.model.PlayerUiModel

fun Player.toUiModel(): PlayerUiModel {
    return PlayerUiModel(
        id = id,
        shirtNumber = shirtNumber,
        name = name,
        position = position.toUiModel(),
        nationality = nationality,
    )
}

fun List<Player>.toUiModels(): List<PlayerUiModel> {
    return map(Player::toUiModel)
}

private fun Position.toUiModel(): PlayerPositionUi {
    return when (this) {
        Position.GOALKEEPER -> PlayerPositionUi.GOALKEEPER
        Position.DEFENDER -> PlayerPositionUi.DEFENDER
        Position.MIDFIELDER -> PlayerPositionUi.MIDFIELDER
        Position.FORWARD -> PlayerPositionUi.FORWARD
    }
}