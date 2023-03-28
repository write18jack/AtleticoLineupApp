package com.example.atleticolineupapp.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LastFormation(
    @PrimaryKey
    val id: Int = 1,
    @ColumnInfo
    var itemLastFormation: String
)
