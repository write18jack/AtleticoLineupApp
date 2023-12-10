package com.whitebeach.atleticolineupapp.data.model.remote.injury

data class PlayerInjury(
    var `get`: String,
    var parameters: Parameters,
    var errors: List<Any>,
    var results: Int,
    var paging: Paging,
    var response: List<Response>
)