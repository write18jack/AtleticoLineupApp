package com.whitebeach.atleticolineupapp.data.model.remote.player

import com.whitebeach.data.model.player.Paging
import com.whitebeach.data.model.player.Parameters
import com.whitebeach.data.model.player.ResponseX

data class PlayersResponse(
    var `get`: String,
    var parameters: Parameters,
    var errors: List<Any>,
    var results: Int,
    var paging: Paging,
    var response: List<ResponseX>
)