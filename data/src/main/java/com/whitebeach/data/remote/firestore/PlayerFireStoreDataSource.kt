package com.whitebeach.data.remote.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.whitebeach.data.model.PlayerInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun playerFireStoreDataSource(): StateFlow<List<PlayerInfo>> {
    val db = FirebaseFirestore.getInstance()

    val userList: MutableStateFlow<List<PlayerInfo>> = MutableStateFlow(emptyList())

    db.collection("Players")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                val result = document.toObject(PlayerInfo::class.java)
                if (true) {
                    userList.value = userList.value + result
                }
            }
        }.addOnFailureListener {
            userList.value = emptyList()
        }
    return userList
}

