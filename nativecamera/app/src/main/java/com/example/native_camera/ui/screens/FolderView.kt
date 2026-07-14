package com.example.native_camera.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.io.File

@Composable
fun FolderView(
    onBackClick: () -> Unit,
    onPhotoClick: (File) -> Unit,
    modifier: Modifier = Modifier
) {
    // 1. Component State (Empty array for now)
    var imageFiles by remember { mutableStateOf(listOf<File>()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Button(onClick = onBackClick) {
            Text("← Back to Menu")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Your Saved Images Directory",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Conditional rendering (Our Jetpack Compose *ngIf block)
        if (imageFiles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No images captured yet. Go snap some photos!",
                    color = Color.Gray
                )
            }
        } else {
            // Displays files in a responsive grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(imageFiles) { file ->
                    PhotoGridItem(file = file, onClick = { onPhotoClick(file) })
                }
            }
        }
    }
}

// Reusable sub-composable representing a single photo file item
@Composable
fun PhotoGridItem(file: File, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .clickable { onClick() },
        contentAlignment = Alignment.BottomStart
    ) {
        Text(
            text = file.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(8.dp),
            color = Color.White
        )
    }
}
