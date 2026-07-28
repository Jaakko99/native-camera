package com.example.native_camera.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.native_camera.ui.screens.CameraView
import com.example.native_camera.ui.screens.FolderView
import com.example.native_camera.ui.screens.MainMenu
import kotlinx.serialization.Serializable

// 1. Define type-safe route targets (Using kotlinx.serialization.Serializable)
@Serializable
object MenuRoute

@Serializable
object CameraRoute

@Serializable
object FolderRoute

// 2. Wrap everything inside a @Composable function!
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    // rememberNavController belongs inside a @Composable scope
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MenuRoute,
        modifier = modifier
    ) {
        composable<MenuRoute> {
            MainMenu(
                onOpenCamera = { navController.navigate(CameraRoute) },
                onOpenFolder = { navController.navigate(FolderRoute) }
            )
        }

        composable<CameraRoute> {
            CameraView(
                onBackClick = { navController.popBackStack() }
            )
        }


        composable<FolderRoute> {
            FolderView(
                onBackClick = { navController.popBackStack() },
                onPhotoClick = { selectedFile ->
                }
            )
        }
    }
}