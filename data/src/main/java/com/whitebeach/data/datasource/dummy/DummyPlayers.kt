package com.whitebeach.data.datasource.dummy

import com.whitebeach.domain.model.Player
import com.whitebeach.domain.model.Position

internal val dummyPlayers = listOf(
    Player(
        id = 1,
        shirtNumber = 13,
        name = "Jan Oblak",
        position = Position.GOALKEEPER,
        nationality = "Slovenia",
        imageUrl = ""
    ),
    Player(
        id = 2,
        shirtNumber = 2,
        name = "José María Giménez",
        position = Position.DEFENDER,
        nationality = "Uruguay",
        imageUrl = ""
    ),
    Player(
        id = 3,
        shirtNumber = 15,
        name = "Clément Lenglet",
        position = Position.DEFENDER,
        nationality = "France",
        imageUrl = ""
    ),
    Player(
        id = 4,
        shirtNumber = 14,
        name = "Marcos Llorente",
        position = Position.MIDFIELDER,
        nationality = "Spain",
        imageUrl = ""
    ),
    Player(
        id = 5,
        shirtNumber = 6,
        name = "Koke",
        position = Position.MIDFIELDER,
        nationality = "Spain",
        imageUrl = ""
    ),
    Player(
        id = 6,
        shirtNumber = 7,
        name = "Antoine Griezmann",
        position = Position.FORWARD,
        nationality = "France",
        imageUrl = ""
    ),
    Player(
        id = 7,
        shirtNumber = 9,
        name = "Alexander Sørloth",
        position = Position.FORWARD,
        nationality = "Norway",
        imageUrl = ""
    ),
)