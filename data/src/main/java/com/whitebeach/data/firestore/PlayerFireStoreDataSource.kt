package com.whitebeach.data.firestore

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.firestore.FirebaseFirestore
import com.whitebeach.data.model.PlayerInfo

fun playerFireStoreDataSource(): SnapshotStateList<PlayerInfo> {
    val db = FirebaseFirestore.getInstance()
    val userList = mutableStateListOf<PlayerInfo>()

    db.collection("Players")
        .addSnapshotListener { value, error ->
            if (error != null) {
                Log.d("error", "getDataFromFirstStore: $error")
            }

            if (value != null) {
                val list = value.documents
                for (document in list) {
                    val result = document.toObject(PlayerInfo::class.java)
                    if (result != null) {
                        Log.d("result", "PlayerDataSource: $result")
                        userList.add(result)
                    }
                }
            }
        }
    return userList
}

