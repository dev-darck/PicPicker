package com.project.photodetail.viewmodel

import com.project.model.Photo

sealed class PhotoState {
    object Loading : PhotoState()
    data class Success(val result: Photo) : PhotoState()
    object Exception : PhotoState()
}

internal fun PhotoState.updateResult(
    result: Photo,
): PhotoState =
    when (this) {
        is PhotoState.Success -> PhotoState.Success(result)
        else -> PhotoState.Success(result)
    }