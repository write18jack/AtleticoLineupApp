package com.whitebeach.data.remote.model.player

import com.whitebeach.atleticolineupapp.data.model.remote.player.Player
import com.whitebeach.atleticolineupapp.data.model.remote.player.Statistic

data class ResponseX(
    var player: Player,
    var statistics: List<Statistic>
)