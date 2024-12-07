package com.whitebeach.presentation.ui.main.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

suspend fun shareNoteImage(bitmap: Bitmap, context: Context) {
    val uri = createUri(bitmap, context)
    if (uri != null) {
        //shareImageUri(context, uri)
    } else {
        Toast.makeText(
            context,
            "uri is null",
            Toast.LENGTH_SHORT
        ).show()
    }
}

suspend fun createUri(image: Bitmap, context: Context): Uri? =
    withContext(Dispatchers.IO) {
        val imagesFolder = File(context.cacheDir, "images")
        var uri: Uri? = null
        try {
            imagesFolder.mkdirs()
            val file = File(imagesFolder, "shared_image.png")
            val stream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.flush()
            stream.close()
            uri = FileProvider.getUriForFile(
                context,
                "com.atletico.file-provider",
                file
            ) //BuildConfig.APPLICATION_ID + ".provider"
        } catch (e: IOException) {
            Log.d("Error", "IOException while trying to write file for sharing: " + e.message)
        }
        uri
    }

fun shareImageUri(uri: Uri): Intent {
    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.type = "image/png"
    return intent
}
