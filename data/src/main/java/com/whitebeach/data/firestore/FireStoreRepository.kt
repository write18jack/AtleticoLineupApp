package com.whitebeach.data.firestore

import com.whitebeach.data.model.PlayerInfo

interface FireStoreRepository {
    fun getPlayers(): List<PlayerInfo>
}

