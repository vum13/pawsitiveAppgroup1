package com.pawspetacademy.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.pawspetacademy.app.R
import com.pawspetacademy.app.ui.components.*
import com.pawspetacademy.app.ui.theme.PawShape

private val galleryPhotos = listOf(
    R.drawable.gallery_01, R.drawable.gallery_02, R.drawable.gallery_03, R.drawable.gallery_04,
    R.drawable.gallery_05, R.drawable.gallery_06, R.drawable.gallery_07, R.drawable.gallery_08,
    R.drawable.gallery_09, R.drawable.gallery_10, R.drawable.gallery_11, R.drawable.gallery_12,
)

@Composable
fun GalleryScreen(onNavigate: (String) -> Unit, onBack: () -> Unit) {
    AppScaffold("Gallery", onNavigate, onBack) {
        Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                "Explore the fun had learning", Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold, fontSize = 16.sp, textAlign = TextAlign.Center
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(galleryPhotos) { photo ->
                    Image(
                        painter = painterResource(photo),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(100.dp).clip(PawShape)
                    )
                }
            }
        }
    }
}
