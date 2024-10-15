package com.whitebeach.data.remote.firestore

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.firestore.FirebaseFirestore
import com.whitebeach.data.model.PlayerInfo

fun playerFireStoreDataSource(): SnapshotStateList<PlayerInfo> {
    val db = FirebaseFirestore.getInstance()
    var userList = mutableStateListOf<PlayerInfo>()

    db.collection("Players")
        .addSnapshotListener { value, error ->
            error?.let {
                Log.d("error", "getDataFromFirstStore: $it")
            }
            if (value != null) {
                for (document in value.documents) {
                    val result = document.toObject(PlayerInfo::class.java)
                    if (result != null) {
                        Log.d("result", "PlayerDataSource: $result")
                        userList.add(result)
                    }
                }
            }

//            value?.documents
//                ?.mapNotNull { document -> document.toObject(PlayerInfo::class.java) }
//                .also { result ->
//                    Log.d("result", "PlayerDataSource: $result")
//                    userList.addAll(result)
//                }
        }
    return userList
}

