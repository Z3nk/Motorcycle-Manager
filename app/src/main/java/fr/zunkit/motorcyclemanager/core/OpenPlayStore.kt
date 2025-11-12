package fr.zunkit.motorcyclemanager.core

import android.content.Context
import androidx.core.net.toUri

fun openPlayStore(context: Context) {
    val uri = "market://details?id=${context.packageName}".toUri()
    val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, uri)
    intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NO_HISTORY or android.content.Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
    try {
        context.startActivity(intent)
    } catch (e: android.content.ActivityNotFoundException) {
        val webUri = "https://play.google.com/store/apps/details?id=${context.packageName}".toUri()
        context.startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW, webUri))
    }
}