package com.whitebeach.data.model.player

data class Statistic(
    var team: Team,
    var league: League,
    var games: Games,
    var substitutes: Substitutes,
    var shots: Shots,
    var goals: Goals,
    var passes: Passes,
    var tackles: Tackles,
    var duels: Duels,
    var dribbles: Dribbles,
    var fouls: Fouls,
    var cards: Cards,
    var penalty: Penalty
)