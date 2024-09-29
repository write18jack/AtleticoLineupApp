package com.whitebeach.data.remote.model.injury

data class League(
    var id: Int,
    var season: Int,
    var name: String,
    var country: String,
    var logo: String,
    var flag: String?
)