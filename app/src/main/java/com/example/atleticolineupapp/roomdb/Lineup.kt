package com.example.atleticolineupapp.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Lineup(
    @PrimaryKey
    @ColumnInfo(name = "position")
    val itemPosition: Int,
    @ColumnInfo(name = "player")
    val itemPlayer: Int
)
