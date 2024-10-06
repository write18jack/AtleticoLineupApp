package com.whitebeach.atleticolineupapp.data.model.remote.player

import com.whitebeach.data.model.player.Cards
import com.whitebeach.data.model.player.Dribbles
import com.whitebeach.data.model.player.Duels
import com.whitebeach.data.model.player.Fouls
import com.whitebeach.data.model.player.Games
import com.whitebeach.data.model.player.Goals
import com.whitebeach.data.model.player.League
import com.whitebeach.data.model.player.Passes
import com.whitebeach.data.model.player.Penalty
import com.whitebeach.data.model.player.Shots
import com.whitebeach.data.model.player.Substitutes
import com.whitebeach.data.model.player.Tackles
import com.whitebeach.data.model.player.Team

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