package com.whitebeach.data.remote.model.injury

import com.whitebeach.data.remote.model.injury.Paging
import com.whitebeach.data.remote.model.injury.Parameters
import com.whitebeach.data.remote.model.injury.Response

data class PlayerInjury(
    var `get`: String,
    var parameters: Parameters,
    var errors: List<Any>,
    var results: Int,
    var paging: Paging,
    var response: List<Response>
)