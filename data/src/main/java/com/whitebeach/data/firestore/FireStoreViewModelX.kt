package com.whitebeach.data.firestore


//interface StorageService {
//    val tasks: Flow<List<PlayerInfo>>
//    suspend fun getTask(): List<PlayerInfo>
//
//}
//
//class StorageServiceImpl
//@Inject
//constructor(private val firestore: FirebaseFirestore) :
//    StorageService {
//    @OptIn(ExperimentalCoroutinesApi::class)
//    override val tasks: Flow<List<PlayerInfo>>
//        get() =
//            firestore.collection("Players").dataObjects()
//
//    override suspend fun getTask(): List<PlayerInfo> {
//        val playerInfoList = mutableListOf<PlayerInfo>()
//        firestore.collection("Players").addSnapshotListener { snapshot, e ->
//            if (e != null) {
//                Log.w(TAG, "Listen failed.", e)
//                return@addSnapshotListener
//            }
//            // 取得したデータをusers変数に格納
//            for (doc in snapshot!!) {
//                doc.toObject(PlayerInfo::class.java).let {
//                    playerInfoList.add(it)
//                }
//            }
//        }
//        // StateFlowの値を更新
//        return playerInfoList
//    }
//}
//
//@Module
//@InstallIn(SingletonComponent::class)
//object FirebaseModule {
//    @Provides
//    fun firestore(): FirebaseFirestore = Firebase.firestore
//}
//
//@Module
//@InstallIn(SingletonComponent::class)
//abstract class ServiceModule {
//
//    @Binds
//    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
//}


