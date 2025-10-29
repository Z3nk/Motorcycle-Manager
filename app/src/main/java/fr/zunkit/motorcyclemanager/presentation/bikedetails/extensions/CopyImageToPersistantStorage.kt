package fr.zunkit.motorcyclemanager.presentation.bikedetails.extensions

import android.content.Context
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

fun Context.copyImageToPersistentStorage(uri: Uri, bikeId: Long): String {
    val bikeDir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "bikes")
    bikeDir.mkdirs()

    val file = File(bikeDir, "bike_${bikeId}.jpg")

    contentResolver.openInputStream(uri)?.use { input ->
        FileOutputStream(file).use { output ->
            input.copyTo(output)
        }
    }
    return file.absolutePath
}