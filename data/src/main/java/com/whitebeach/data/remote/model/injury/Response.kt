package com.whitebeach.data.remote.model.injury

import com.whitebeach.data.remote.model.injury.Fixture
import com.whitebeach.data.remote.model.injury.League
import com.whitebeach.data.remote.model.injury.Player
import com.whitebeach.data.remote.model.injury.Team

data class Response(
    var player: Player,
    var team: Team,
    var fixture: Fixture,
    var league: League
)