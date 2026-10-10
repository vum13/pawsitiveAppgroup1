package com.pawsitive.petacademy.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pawsitive.petacademy.data.PhotoStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    private val _photos = MutableStateFlow(PhotoStorage.listPhotos(application))
    val photos: StateFlow<List<Uri>> = _photos.asStateFlow()

    /** Called after CameraX has saved a photo into app storage. */
    fun addPhoto(uri: Uri) = _photos.update { listOf(uri) + it }

    /** Copies picked images into app storage so they stay available after the picker grant expires. */
    fun importFromGallery(uris: List<Uri>) {
        viewModelScope.launch(Dispatchers.IO) {
            val app = getApplication<Application>()
            val imported = uris.mapNotNull { uri ->
                runCatching {
                    val file = PhotoStorage.newPhotoFile(app, "GAL")
                    val input = app.contentResolver.openInputStream(uri) ?: error("Cannot open $uri")
                    input.use { src -> file.outputStream().use { dst -> src.copyTo(dst) } }
                    Uri.fromFile(file)
                }.getOrNull()
            }
            if (imported.isNotEmpty()) _photos.update { imported + it }
        }
    }
}
