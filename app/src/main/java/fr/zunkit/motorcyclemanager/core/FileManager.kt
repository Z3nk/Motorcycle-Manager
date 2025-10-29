package fr.zunkit.motorcyclemanager.core

import android.content.Context
import android.net.Uri
import android.os.Environment
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import javax.inject.Inject

interface FileManager {
    fun copyImageToPersistentStorage(uri: Uri, bikeId: Long): String
    fun getBikePhotoDirectory(): File
}

class FileManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : FileManager {

    override fun copyImageToPersistentStorage(uri: Uri, bikeId: Long): String {
        val bikeDir = getBikePhotoDirectory()
        val file = File(bikeDir, "bike_${bikeId}.jpg")

        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        } ?: throw FileNotFoundException()

        return file.absolutePath
    }

    override fun getBikePhotoDirectory(): File {
        val dir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "bikes")
        dir.mkdirs()
        return dir
    }
}