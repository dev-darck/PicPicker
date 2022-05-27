package com.project.bottomsheet_image_piker

import android.content.ContentResolver
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagePikerViewModel @Inject constructor(
    private val contentResolver: ContentResolver,
) : ViewModel() {

    private val mutableImages: MutableSharedFlow<List<GalleryImage>> =
        MutableSharedFlow(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val images: SharedFlow<List<GalleryImage>> = mutableImages.asSharedFlow()

    fun getImageFromGallery() {
        viewModelScope.launch(Dispatchers.IO) {
            val listData = ArrayList<GalleryImage>()
            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val projection = arrayOf(
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media._ID
            )

            val orderBy = MediaStore.Images.Media.DATE_TAKEN
            val cursor = contentResolver.query(uri, projection, null, null,
                "$orderBy $ORDER") ?: return@launch
            if (cursor.moveToFirst()) {
                do {
                    val path =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                    listData.add(GalleryImage().apply {
                        this.path = path
                    })
                } while (cursor.moveToNext())
                cursor.close()
            }
            mutableImages.emit(listData)
        }
    }

    private companion object {
        const val ORDER = "DESC"
    }
}