package com.project.hometab.screen

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.model.PhotoModel
import com.project.unsplash_api.ResultWrapper
import com.project.unsplash_api.api.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val unsplashRepository: UnsplashRepository,
) : ViewModel() {

    private var screenDensity = 0F
    private var screenWidthDp = 0.dp
    private var spanCount = 2
    private val columnWidth
        get() = screenWidthDp / spanCount - 20.dp

    private val _newPhotosFlow = MutableStateFlow<HomeState>(HomeState.Loading)
    val newPhotosFlow = _newPhotosFlow.asStateFlow()


    fun photoFirstPage(spanCount: Int) {
        this.spanCount = spanCount
        val state = _newPhotosFlow.value
        if (state is HomeState.Success && state.result.item.isNotEmpty()) return
        photos(1)
    }

    fun setNeedSize(screenDensity: Float, screenWidthDp: Dp) {
        this.screenDensity = screenDensity
        this.screenWidthDp = screenWidthDp
    }

    fun photos(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = unsplashRepository.new(page)
            if (result is ResultWrapper.Success) {
                _newPhotosFlow.update {
                    val homeModel = measureResult(result.value)
                    it.updateResult(homeModel, page)
                }
            }
        }
    }

    private fun measureResult(result: List<PhotoModel>): List<PhotoModel> =
        result.map { photoModel ->
            photoModel.aspectRatio = photoModel.width / photoModel.height
            photoModel.measureheight =
                ((columnWidth.value / (photoModel.width / screenDensity)) * photoModel.height + 20 * screenDensity).toInt()
            photoModel
        }
}