package com.whitebeach.data.remote.firestore

import com.whitebeach.data.model.PlayerInfo

interface FireStoreRepository {
    suspend fun getPlayers(): List<PlayerInfo>
}

class FireStoreRepositoryImpl(): FireStoreRepository {
    override suspend fun getPlayers(): List<PlayerInfo> =
        playerFireStoreDataSource()

}
