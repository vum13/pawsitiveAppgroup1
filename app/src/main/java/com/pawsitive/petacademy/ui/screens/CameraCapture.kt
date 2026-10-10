package com.pawsitive.petacademy.ui.screens

import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.pawsitive.petacademy.data.PhotoStorage
import com.pawsitive.petacademy.ui.theme.PawOrange

/**
 * Full-screen CameraX preview with capture, close and front/back switch.
 * Photos are written to app storage (PhotoStorage) and reported through [onPhotoSaved].
 */
@Composable
fun CameraCapture(
    onPhotoSaved: (Uri) -> Unit,
    onError: (String) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val reportError: (String) -> Unit = onError

    val previewView = remember {
        PreviewView(context).apply { scaleType = PreviewView.ScaleType.FILL_CENTER }
    }
    val imageCapture = remember {
        ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build()
    }
    var lensFacing by remember { mutableIntStateOf(CameraSelector.LENS_FACING_BACK) }
    var isCapturing by remember { mutableStateOf(false) }

    DisposableEffect(lensFacing, lifecycleOwner) {
        val providerFuture = ProcessCameraProvider.getInstance(context)
        providerFuture.addListener({
            try {
                val provider = providerFuture.get()
                val selector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
                if (!provider.hasCamera(selector)) {
                    onError("That camera is not available on this device")
                    lensFacing = CameraSelector.LENS_FACING_BACK
                } else {
                    val preview = Preview.Builder().build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)
                    provider.unbindAll()
                    provider.bindToLifecycle(lifecycleOwner, selector, preview, imageCapture)
                }
            } catch (e: Exception) {
                onError("Could not start the camera: ${e.message}")
            }
        }, ContextCompat.getMainExecutor(context))

        onDispose {
            if (providerFuture.isDone) {
                runCatching { providerFuture.get().unbindAll() }
            }
        }
    }

    fun capture() {
        if (isCapturing) return
        isCapturing = true
        previewView.display?.rotation?.let { imageCapture.targetRotation = it }
        val file = PhotoStorage.newPhotoFile(context, "IMG")
        val options = ImageCapture.OutputFileOptions.Builder(file).build()
        imageCapture.takePicture(
            options,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    isCapturing = false
                    onPhotoSaved(Uri.fromFile(file))
                }

                override fun onError(exception: ImageCaptureException) {
                    isCapturing = false
                    reportError("Could not save photo: ${exception.message}")
                }
            }
        )
    }

    Box(modifier.fillMaxSize().background(Color.Black)) {
        AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onClose,
                modifier = Modifier.size(52.dp).clip(CircleShape).background(Color.Black.copy(alpha = 0.5f))
            ) {
                Icon(Icons.Filled.Close, contentDescription = "Close camera", tint = Color.White)
            }

            Box(
                modifier = Modifier
                    .size(78.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(6.dp)
                    .clip(CircleShape)
                    .background(if (isCapturing) Color.Gray else PawOrange)
                    .clickable(enabled = !isCapturing) { capture() }
            )

            IconButton(
                onClick = {
                    lensFacing =
                        if (lensFacing == CameraSelector.LENS_FACING_BACK) CameraSelector.LENS_FACING_FRONT
                        else CameraSelector.LENS_FACING_BACK
                },
                modifier = Modifier.size(52.dp).clip(CircleShape).background(Color.Black.copy(alpha = 0.5f))
            ) {
                Icon(Icons.Filled.Cameraswitch, contentDescription = "Switch camera", tint = Color.White)
            }
        }
    }
}
