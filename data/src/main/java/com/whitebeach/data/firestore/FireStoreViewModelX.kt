package com.whitebeach.data.firestore

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.whitebeach.atleticolineupapp.data.model.remote.PlayerInfo
import com.whitebeach.data.remote.model.PlayerInfo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface StorageService {
    val tasks: Flow<List<PlayerInfo>>
    suspend fun getTask(): List<PlayerInfo>

}

class StorageServiceImpl
@Inject
constructor(private val firestore: FirebaseFirestore) :
    StorageService {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val tasks: Flow<List<PlayerInfo>>
        get() =
            firestore.collection("Players").dataObjects()

    override suspend fun getTask(): List<PlayerInfo> {
        val playerInfoList = mutableListOf<PlayerInfo>()
        firestore.collection("Players").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            // 取得したデータをusers変数に格納
            for (doc in snapshot!!) {
                doc.toObject(PlayerInfo::class.java).let {
                    playerInfoList.add(it)
                }
            }
        }
        // StateFlowの値を更新
        return playerInfoList
    }
}

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    fun firestore(): FirebaseFirestore = Firebase.firestore
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
}

@HiltViewModel
class FireStoreViewModelX @Inject constructor(
    storageService: StorageService
) : ViewModel() {

    val tasks = storageService.tasks

//    private var _players = MutableStateFlow<List<PlayerInfo>>(emptyList())
//    val players: StateFlow<List<PlayerInfo>> = _players.asStateFlow()
//
//    var state = mutableStateListOf<PlayerInfo>()
//    private val db = FirebaseFirestore.getInstance()
    init {
//        db.collection("Players")
//            .addSnapshotListener { snapshot, e ->
//                if (e != null) {
//                    Log.w(TAG, "Listen failed.", e)
//                    return@addSnapshotListener
//                }
//
//                val playerInfoList = mutableListOf<PlayerInfo>()
//                // 取得したデータをusers変数に格納
//                for (doc in snapshot!!) {
//                    doc.toObject(PlayerInfo::class.java).let {
//                        playerInfoList.add(it)
//                    }
//                }
//                // StateFlowの値を更新
//                _players.value = playerInfoList
//            }
    }
//    private fun getData() {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                _players = getDataFromFireStore()
//            }
//        }
//    }
}

//fun getDataFromFireStore(): MutableStateFlow<List<PlayerInfo>> {
//    val db = FirebaseFirestore.getInstance()
//    val playerInfoList = MutableStateFlow<List<PlayerInfo>>(emptyList())
//
//    try {
//        db.collection("Players")
//            .get()
//            .addOnSuccessListener { queryDocumentSnapshots ->
//
//            val list = queryDocumentSnapshots.documents
//            for (document in list) {
//                val result = document.toObject(PlayerInfo::class.java)
//                Log.d("firestore check", "$result")
//                if (result != null) {
//                   // playerInfoList.value  = result
//                }
//            }
//        }
//    } catch (e: FirebaseFirestoreException) {
//        Log.d("error", "getDataFromFirstStore: $e")
//    }
//    return playerInfoList
//}
//
