package com.whitebeach.atleticolineupapp.data.model.remote.player

import com.adiupd123.cookmaster.models2.Cards
import com.adiupd123.cookmaster.models2.Dribbles
import com.adiupd123.cookmaster.models2.Duels
import com.adiupd123.cookmaster.models2.Fouls
import com.adiupd123.cookmaster.models2.Games
import com.adiupd123.cookmaster.models2.Goals
import com.adiupd123.cookmaster.models2.League
import com.adiupd123.cookmaster.models2.Passes
import com.adiupd123.cookmaster.models2.Penalty
import com.adiupd123.cookmaster.models2.Shots
import com.adiupd123.cookmaster.models2.Substitutes
import com.adiupd123.cookmaster.models2.Tackles
import com.adiupd123.cookmaster.models2.Team

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