package com.whitebeach.atleticolineupapp.data.model.remote.player

data class Player(
    var id: Int,
    var name: String,
    var firstname: String?,
    var lastname: String?,
    var age: Int,
    var birth: Birth,
    var nationality: String?,
    var height: String?,
    var weight: String?,
    var injured: Boolean,
    var photo: String
)