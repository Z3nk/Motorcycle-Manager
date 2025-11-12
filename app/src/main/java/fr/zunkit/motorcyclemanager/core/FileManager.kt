package fr.zunkit.motorcyclemanager.core

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import javax.inject.Inject

interface FileManager {
    fun copyImageToPersistentStorage(uri: Uri, fileName: String): String
    fun getBikePhotoDirectory(): File

    fun getFileExtension(uri: Uri): String?
    fun getFileName(uri: Uri): String?
}

class FileManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : FileManager {

    override fun copyImageToPersistentStorage(uri: Uri, fileName: String): String {
        val bikeDir = getBikePhotoDirectory()
        val file = File(bikeDir, fileName)

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

    override fun getFileExtension(uri: Uri): String? {
        return when (uri.scheme) {
            ContentResolver.SCHEME_CONTENT -> {
                val type = context.contentResolver.getType(uri)
                MimeTypeMap.getSingleton().getExtensionFromMimeType(type)
            }
            ContentResolver.SCHEME_FILE -> {
                val file = File(uri.path ?: return null)
                file.extension
            }
            else -> null
        }
    }

    override fun getFileName(uri: Uri): String? {
        return when (uri.scheme) {
            ContentResolver.SCHEME_CONTENT -> {
                var name: String? = null
                val cursor = context.contentResolver.query(uri, null, null, null, null)
                cursor?.use {
                    val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (it.moveToFirst() && nameIndex >= 0) {
                        name = it.getString(nameIndex)
                    }
                }
                name
            }

            ContentResolver.SCHEME_FILE -> {
                File(uri.path ?: return null).name
            }

            else -> null
        }
    }
}