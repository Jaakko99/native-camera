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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.native_camera.ui.theme.NativecameraTheme
import com.example.native_camera.ui.screens.CameraView
import com.example.native_camera.ui.screens.FolderView
import com.example.native_camera.ui.screens.MainMenu
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NativecameraTheme {
                val context = LocalContext.current
                var currentScreen by remember { mutableStateOf("home") }

                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { isGranted: Boolean ->
                    if (isGranted) {
                        currentScreen = "camera"
                    } else {
                        Toast.makeText(context, "Camera permission is required.", Toast.LENGTH_SHORT).show()
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (currentScreen) {
                        "camera" -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                CameraView(modifier = Modifier.padding(innerPadding))
                            } else {
                                Text(text = "Device Android version too low.", modifier = Modifier.padding(innerPadding))
                            }
                        }
                        "folder" -> {
                            FolderView(
                                modifier = Modifier.padding(innerPadding),
                                onBackClick = { currentScreen = "home" },
                                onPhotoClick = { file ->
                                    // We will handle photo click detail / ML analysis here later!
                                }
                            )
                        }
                        else -> {
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