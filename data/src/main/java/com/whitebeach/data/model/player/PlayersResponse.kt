package com.whitebeach.data.model.player

data class PlayersResponse(
    var `get`: String,
    var parameters: Parameters,
    var errors: List<Any>,
    var results: Int,
    var paging: Paging,
    var response: List<ResponseX>
)