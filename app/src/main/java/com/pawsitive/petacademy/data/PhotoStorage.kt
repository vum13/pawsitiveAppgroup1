package com.pawsitive.petacademy.data

import android.content.Context
import android.net.Uri
import java.io.File

object PhotoStorage {

    private fun photosDir(context: Context): File =
        File(context.filesDir, "photos").apply { if (!exists()) mkdirs() }

    fun newPhotoFile(context: Context, prefix: String = "IMG"): File =
        File(photosDir(context), "${prefix}_${System.currentTimeMillis()}_${(0..9999).random()}.jpg")

    fun listPhotos(context: Context): List<Uri> =
        photosDir(context).listFiles()
            ?.filter { it.isFile && it.length() > 0 }
            ?.sortedByDescending { it.lastModified() }
            ?.map { Uri.fromFile(it) }
            ?: emptyList()
}
