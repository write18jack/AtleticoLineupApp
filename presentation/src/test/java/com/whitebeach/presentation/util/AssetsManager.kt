package com.whitebeach.presentation.util

import java.io.InputStream

object AssetsManager {
    fun openStream(pathFile: String): InputStream? = javaClass.classLoader?.getResourceAsStream(pathFile)
}