package com.whitebeach.data.remote.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.whitebeach.data.model.PlayerInfo

fun playerFireStoreDataSource(): List<PlayerInfo> {
    val db = FirebaseFirestore.getInstance()
    var userList = listOf<PlayerInfo>()

    db.collection("Players")
        .addSnapshotListener { value, error ->
            error?.let {
                Log.d("error", "getDataFromFirstStore: $it")
            }
            value?.documents
                ?.mapNotNull { document -> document.toObject(PlayerInfo::class.java) }
                .also { result ->
                    Log.d("result", "PlayerDataSource: $result")
                    userList = result as List<PlayerInfo>
                }
        }
    return userList
}

