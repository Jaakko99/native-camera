package com.example.native_camera

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.native_camera.ui.theme.NativecameraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NativecameraTheme {
                val context = LocalContext.current

                // 1. Manage state via a unified "currentScreen" router layout
                var currentScreen by remember { mutableStateOf("home") }

                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { isGranted: Boolean ->
                    if (isGranted) {
                        Toast.makeText(context, "Permission Granted!", Toast.LENGTH_SHORT).show()
                        currentScreen = "camera" // Route to camera on success
                    } else {
                        Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // 2. Clear Screen Controller Architecture
                    when (currentScreen) {
                        "camera" -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                CameraPreviewView(modifier = Modifier.padding(innerPadding))
                            } else {
                                Text(text = "Device Android version too low.", modifier = Modifier.padding(innerPadding))
                            }
                        }
                        "folder" -> {
                            // This is where our folder view gets drawn!
                            FolderView(
                                modifier = Modifier.padding(innerPadding),
                                onBackClick = { currentScreen = "home" }
                            )
                        }
                        else -> {
                            // Home Menu: Combines buttons cleanly into one column instead of fighting for screen space
                            MainMenu(
                                onOpenCamera = { permissionLauncher.launch(Manifest.permission.CAMERA) },
                                onOpenFolder = { currentScreen = "folder" },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)

@Composable

fun CameraPreviewView(modifier: Modifier = Modifier) {

    val lifecycleOwner = LocalLifecycleOwner.current



    AndroidView(

        factory = { ctx ->

            PreviewView(ctx).apply {

                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)



                cameraProviderFuture.addListener({

                    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()



                    val preview = Preview.Builder().build().also {

                        it.setSurfaceProvider(this.surfaceProvider)

                    }



                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA



                    try {

                        cameraProvider.unbindAll()

                        cameraProvider.bindToLifecycle(

                            lifecycleOwner,

                            cameraSelector,

                            preview

                        )

                    } catch (exc: Exception) {

                        android.util.Log.e("CameraPreview", "Use case binding failed", exc)

                    }

                }, ContextCompat.getMainExecutor(ctx))

            }

        },

        modifier = modifier.fillMaxSize()

    )

}

@Composable
fun MainMenu(
    onOpenCamera: () -> Unit,
    onOpenFolder: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onOpenCamera) {
            Text("Open Camera")
        }

        Spacer(modifier = Modifier.height(16.dp)) // Nice breathing room between buttons

        Button(onClick = onOpenFolder) {
            Text("Open Folder")
        }
    }
}

@Composable
fun FolderView(onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Button(onClick = onBackClick) {
            Text("← Back to Menu")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Your Saved Images Directory", style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)

        // This is a placeholder text block where our File I/O grid list will eventually live!
        Text(
            text = "No images captured yet. Go snap some photos!",
            modifier = Modifier.padding(top = 16.dp),
            color = androidx.compose.ui.graphics.Color.Gray
        )
    }
}

data class Message(val author: String, val body: String)