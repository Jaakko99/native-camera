package com.example.native_camera.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainMenu(
    onOpenCamera: () -> Unit,
    onOpenFolder: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onOpenCamera,
            modifier = Modifier.height(56.dp)
        ) {
            Text("Open Camera View")
        }

        Spacer(modifier = Modifier.height(16.dp)) // Nice breathing room between buttons

        Button(
            onClick = onOpenFolder,
            modifier = Modifier.height(56.dp)
        ) {
            Text("Open Local Folder")
        }
    }
}
