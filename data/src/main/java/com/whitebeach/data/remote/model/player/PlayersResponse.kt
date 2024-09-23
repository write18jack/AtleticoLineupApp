package com.whitebeach.atleticolineupapp.data.model.remote.player

import com.adiupd123.cookmaster.models2.Paging
import com.adiupd123.cookmaster.models2.Parameters

data class PlayersResponse(
    var `get`: String,
    var parameters: Parameters,
    var errors: List<Any>,
    var results: Int,
    var paging: Paging,
    var response: List<ResponseX>
)