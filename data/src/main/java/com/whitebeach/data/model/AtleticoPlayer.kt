package com.whitebeach.data.model

data class AtleticoPlayer(
    var area: Area,
    var id: Int,
    var name: String,
    var shortName: String,
    var tla: String,
    var crest: String,
    var address: String,
    var website: String,
    var founded: Int,
    var clubColors: String,
    var venue: String,
    var runningCompetitions: List<RunningCompetition>,
    var coach: Coach,
    var squad: List<Squad>,
    var staff: List<Any?>,
    var lastUpdated: String
)

data class Area(
    var id: Int,
    var name: String,
    var code: String,
    var flag: String
)

data class RunningCompetition(
    var id: Int,
    var name: String,
    var code: String,
    var type: String,
    var emblem: String
)

data class Coach(
    var id: Int,
    var firstName: String,
    var lastName: String,
    var name: String,
    var dateOfBirth: String,
    var nationality: String,
    var contract: Contract
)

data class Squad(
    var id: Int,
    var name: String,
    var position: String,
    var dateOfBirth: String,
    var nationality: String
)

data class Contract(
    var start: String,
    var until: String
)