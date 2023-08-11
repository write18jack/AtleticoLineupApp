package com.whitebeach.atleticolineupapp.util

import androidx.annotation.DrawableRes
import com.whitebeach.atleticolineupapp.R


data class PlayerItem(val name: String, @DrawableRes val image: Int, val key: Int = -1)

val PlayerList = listOf(
    PlayerItem("Memphis", R.drawable.memphis),
    PlayerItem("Griezmann", R.drawable.griezmann),
    PlayerItem("Morata", R.drawable.morata),
    PlayerItem("Carrasco", R.drawable.carrasco),
    PlayerItem("Correa", R.drawable.correa),
    PlayerItem("Llorente", R.drawable.llorente),
    PlayerItem("Koke", R.drawable.koke),
    PlayerItem("Saul", R.drawable.saul),
    PlayerItem("Barrios", R.drawable.barrios),
    PlayerItem("Depaul", R.drawable.depaulb),
    PlayerItem("Riquelme", R.drawable.riquelme),
    PlayerItem("Lemar", R.drawable.lemar),
    PlayerItem("Witsel", R.drawable.witsel),
    PlayerItem("Gimenes", R.drawable.gimenez),
    PlayerItem("Morina", R.drawable.morina),
    PlayerItem("Soyuncu", R.drawable.soyuncux),
    PlayerItem("Galan", R.drawable.galan),
    PlayerItem("Hermoso", R.drawable.hermoso),
    PlayerItem("Savic", R.drawable.savic),
    PlayerItem("Azpilicueta", R.drawable.azpi),
    PlayerItem("Mourino", R.drawable.mourino),
    PlayerItem("Reinildo", R.drawable.reinildo),
    PlayerItem("Oblak", R.drawable.oblak),
    PlayerItem("Grbic", R.drawable.grbic),
    )