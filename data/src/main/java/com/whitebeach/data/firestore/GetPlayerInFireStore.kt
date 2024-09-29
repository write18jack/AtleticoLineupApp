package com.whitebeach.data.firestore

//import android.util.Log
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.snapshots.SnapshotStateList
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.FirebaseFirestoreException
//import com.whitebeach.atleticolineupapp.data.model.remote.PlayerInfo
//import com.whitebeach.data.remote.model.PlayerInfo
//
//fun getPlayerInFireStore(): SnapshotStateList<PlayerInfo> {
//    val db = FirebaseFirestore.getInstance()
//    val userList = mutableStateListOf<PlayerInfo>()
//
//    try {
//        db.collection("Players")
//            .get()
//            .addOnSuccessListener { queryDocumentSnapshots ->
//                val list = queryDocumentSnapshots.documents
//                for (document in list) {
//                    val result = document.toObject(PlayerInfo::class.java)
//                    if (result != null) {
//                        userList.add(result)
//                    }
//                }
//            }
//    } catch (e: FirebaseFirestoreException) {
//        Log.d("error", "getDataFromFirstStore: $e")
//    }
//    return userList
//}

