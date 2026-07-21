package com.example.native_camera.data

import android.content.Context
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ImageRepository(private val context: Context) {

    // A StateFlow that holds the list of local image files.
    private val _capturedImages = MutableStateFlow<List<File>>(emptyList())
    val capturedImages: StateFlow<List<File>> = _capturedImages.asStateFlow()

    init {
        // Load existing images on startup
        loadImages()
    }

    // Safely reads files from internal storage
    fun loadImages() {
        val directory = File(context.filesDir, "captures")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val files = directory.listFiles { file -> file.isFile && file.extension == "jpg" }
        _capturedImages.value = files?.toList()?.sortedByDescending { it.lastModified() } ?: emptyList()
    }

    // Saves a photo asynchronously using a Coroutine (Dispatchers.IO)
    suspend fun savePhoto(imageCapture: ImageCapture, executor: Executor): Boolean = withContext(Dispatchers.IO) {
        val directory = File(context.filesDir, "captures")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val photoFile = File(directory, "IMG_${System.currentTimeMillis()}.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        return@withContext suspendCoroutineNet { continuation ->
            imageCapture.takePicture(
                outputOptions,
                executor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        // Reload images on the background thread, which updates the StateFlow!
                        loadImages()
                        continuation.resume(true)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        continuation.resume(false)
                    }
                }
            )
        }
    }
}

// Helper to bridge callback-based CameraX to Kotlin Coroutine suspend functions
suspend inline fun suspendCoroutineNet(
    crossinline block: (kotlin.coroutines.Continuation<Boolean>) -> Unit
): Boolean = suspendCoroutine { block(it) }