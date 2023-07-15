package com.example.atleticolineupapp.util

import androidx.annotation.DrawableRes
import com.example.atleticolineupapp.R

data class PlayerItem(val name: String, @DrawableRes val image: Int, val key: Int = -1)

val PlayerList = listOf(
    PlayerItem("GRIEZMANN", R.drawable.griezmann),
    PlayerItem("Morata", R.drawable.morata),
    PlayerItem("Carrasco", R.drawable.carrasco),
    PlayerItem("Correa", R.drawable.correa),
    PlayerItem("Memphis", R.drawable.memphis),
    PlayerItem("Saul", R.drawable.saul),
    PlayerItem("Depaul", R.drawable.depaul),
    PlayerItem("Lemar", R.drawable.lemar),
    PlayerItem("Jimenes", R.drawable.jimenes),
    PlayerItem("Morina", R.drawable.morina),
    PlayerItem("Grbic", R.drawable.grbic),
    PlayerItem("Hermoso", R.drawable.hermoso),
    PlayerItem("Reinildo", R.drawable.reinildo),
    )