package fr.zunkit.motorcyclemanager.core

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.play.core.review.ReviewManagerFactory
import androidx.core.net.toUri
import kotlinx.coroutines.tasks.await

suspend fun launchInAppReview(context: Context) {
    val activity = context.findActivity() ?: run {
        Log.e("Review", "Activity not found")
        return
    }

    val reviewManager = ReviewManagerFactory.create(context)
    val request = reviewManager.requestReviewFlow()

    try {
        val reviewInfo = request.await()
        val flow = reviewManager.launchReviewFlow(activity, reviewInfo)
        flow.await()
    } catch (e: Exception) {
        Log.e("Review", "In-app review failed", e)
        OpenPlayStore(context)
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is android.content.ContextWrapper -> baseContext.findActivity()
    else -> null
}

