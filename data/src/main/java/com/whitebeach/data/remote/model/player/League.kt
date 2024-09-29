package com.whitebeach.data.remote.model.player

data class League(
    var id: Int?,
    var name: String,
    var country: String?,
    var logo: String?,
    var flag: String?,
    var season: Int
)