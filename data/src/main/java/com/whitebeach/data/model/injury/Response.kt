package com.whitebeach.data.model.injury

data class Response(
    var player: Player,
    var team: Team,
    var fixture: Fixture,
    var league: League
)