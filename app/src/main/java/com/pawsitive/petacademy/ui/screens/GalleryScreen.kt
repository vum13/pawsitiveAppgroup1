package com.pawsitive.petacademy.ui.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pawsitive.petacademy.data.ContentRepository
import com.pawsitive.petacademy.ui.components.Heading
import com.pawsitive.petacademy.ui.components.PawButton
import com.pawsitive.petacademy.ui.components.PawImage
import com.pawsitive.petacademy.ui.theme.Cream
import com.pawsitive.petacademy.ui.theme.InkDark
import com.pawsitive.petacademy.ui.theme.PawOrange
import com.pawsitive.petacademy.ui.theme.PawPurple
import com.pawsitive.petacademy.viewmodel.GalleryViewModel
import kotlinx.coroutines.launch

@Composable
fun GalleryScreen(viewModel: GalleryViewModel = viewModel()) {
    val context = LocalContext.current
    val photos by viewModel.photos.collectAsState()
    var showCamera by remember { mutableStateOf(false) }
    var permissionDenied by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Runtime CAMERA permission
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionDenied = !granted
        if (granted) showCamera = true
    }

    // Photo picker (no storage permission needed; READ_MEDIA_IMAGES is declared in the manifest)
    val pickLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(10)
    ) { uris ->
        if (uris.isNotEmpty()) {
            viewModel.importFromGallery(uris)
            scope.launch { snackbarHostState.showSnackbar("Added ${uris.size} photo(s) to the gallery") }
        }
    }

    fun openCamera() {
        val granted = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED
        if (granted) {
            permissionDenied = false
            showCamera = true
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    if (showCamera) {
        BackHandler { showCamera = false }
        CameraCapture(
            onPhotoSaved = { uri ->
                viewModel.addPhoto(uri)
                showCamera = false
                scope.launch { snackbarHostState.showSnackbar("Photo added to the gallery") }
            },
            onError = { message -> scope.launch { snackbarHostState.showSnackbar(message) } },
            onClose = { showCamera = false }
        )
        return
    }

    Box(Modifier.fillMaxSize().background(Cream)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Heading("Explore the fun had learning")
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        PawButton(
                            text = "Open Camera",
                            onClick = { openCamera() },
                            modifier = Modifier.weight(1f),
                            containerColor = PawOrange,
                            icon = Icons.Filled.CameraAlt
                        )
                        PawButton(
                            text = "Open Gallery",
                            onClick = {
                                pickLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            modifier = Modifier.weight(1f),
                            containerColor = PawPurple,
                            icon = Icons.Filled.PhotoLibrary
                        )
                    }
                    if (permissionDenied) {
                        Column {
                            Text(
                                "Camera permission is needed to take photos.",
                                color = InkDark,
                                fontSize = 13.sp
                            )
                            TextButton(onClick = {
                                context.startActivity(
                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                        data = Uri.fromParts("package", context.packageName, null)
                                    }
                                )
                            }) {
                                Text("Open app settings", color = PawOrange, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    if (photos.isNotEmpty()) {
                        Text("Your photos", color = InkDark, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }

            items(photos, key = { it.toString() }) { uri ->
                PawImage(
                    model = uri,
                    contentDescription = "Your photo",
                    modifier = Modifier.aspectRatio(1f).clip(RoundedCornerShape(10.dp))
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    "Pawsitive moments",
                    color = InkDark,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            items(ContentRepository.galleryImages, key = { it }) { url ->
                PawImage(
                    model = url,
                    contentDescription = "Pet photo",
                    modifier = Modifier.aspectRatio(1f).clip(RoundedCornerShape(10.dp))
                )
            }
        }

        SnackbarHost(snackbarHostState, Modifier.align(Alignment.BottomCenter))
    }
}
