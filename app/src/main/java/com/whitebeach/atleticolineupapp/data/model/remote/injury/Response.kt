package com.whitebeach.atleticolineupapp.data.model.remote.injury

data class Response(
    var player: Player,
    var team: Team,
    var fixture: Fixture,
    var league: League
)