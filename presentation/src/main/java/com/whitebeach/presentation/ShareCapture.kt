package com.whitebeach.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun saveBitmapToCache(context: Context, imagesFolder: File, imageBitmap: ImageBitmap): Uri? {
    var uri: Uri? = null
    try {
        imagesFolder.mkdirs()
        val file = File(imagesFolder, "shared_image.png")
        val stream = FileOutputStream(file)
        imageBitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 90, stream)
        stream.flush()
        stream.close()
        uri = FileProvider.getUriForFile(
            context,
            "com.atletico.file-provider",
            file
        )
    } catch (e: IOException) {
        Log.d("Error", "IOException while trying to write file for sharing: " + e.message)
    }
    return uri
}

fun shareImageBitmap(uri: Uri?): Intent {
    return  Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
}
