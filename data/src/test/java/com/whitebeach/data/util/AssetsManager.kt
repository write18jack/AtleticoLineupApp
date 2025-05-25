package com.whitebeach.data.util

import java.io.InputStream

object AssetsManager {
    fun openStream(pathFile: String): InputStream? = javaClass.classLoader?.getResourceAsStream(pathFile)
}