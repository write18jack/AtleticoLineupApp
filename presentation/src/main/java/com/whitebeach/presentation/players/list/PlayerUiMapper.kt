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
        nationality = nationality.toCountryName(),
        imageUrl = imageUrl,
        birthDate = birthDate,
        birthPlace = birthPlace
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
        Position.UNKNOWN -> PlayerPositionUi.UNKNOWN
    }
}

fun String.toCountryName(): String {
    return when (uppercase()) {
        "SI" -> "Slovenia"
        "ES" -> "Spain"
        "AR" -> "Argentina"
        "BR" -> "Brazil"
        "FR" -> "France"
        "IT" -> "Italy"
        "PT" -> "Portugal"
        "NO" -> "Norway"
        "GB" -> "United Kingdom"
        "US" -> "United States"
        "MX" -> "Mexico"
        "UY" -> "Uruguay"
        "SK" -> "Slovakia"
        "DK" -> "Denmark"
        "NG" -> "Nigeria"
        "KR" -> "Korea"
        else -> this
    }
}