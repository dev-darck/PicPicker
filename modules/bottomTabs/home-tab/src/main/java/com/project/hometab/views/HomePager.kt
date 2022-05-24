package com.project.hometab.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.project.image_loader.GlideImage
import com.project.model.PhotoModel
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomePager(count: Int, pagerState: PagerState, imagesFlow: StateFlow<List<PhotoModel>>) {
    HorizontalPager(
        count = count,
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        val screenDensity = LocalConfiguration.current.densityDpi / 160f
        val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp

        val columnWidth = screenWidthDp / 2 - 26.dp

        val images by imagesFlow.collectAsState()

        var firstListHeight = 0f
        var secondListHeight = 0f
        val firstList = mutableListOf<PhotoModel>()
        val secondList = mutableListOf<PhotoModel>()
        var differencesDp = 0f
        images.forEach { photoModel ->
            if (firstListHeight <= secondListHeight) {
                firstList.add(photoModel)
                firstListHeight += (columnWidth.value / (photoModel.width!!.toFloat() / screenDensity)) * photoModel.height!!
                firstListHeight += 20 * screenDensity
            } else {
                secondList.add(photoModel)
                secondListHeight += (columnWidth.value / (photoModel.width!!.toFloat() / screenDensity)) * photoModel.height!!
                secondListHeight += 20 * screenDensity
            }
            differencesDp = ((firstListHeight - secondListHeight) / screenDensity)
            Timber.d(
                """
                firstListHeight $firstListHeight secondListHeight  $secondListHeight
                firstListSize ${firstList.size} secondListSize  ${secondList.size}
                differencesDp $differencesDp
            """.trimIndent()
            )
        }

        LazyStaggeredGrid(
            differencesDp = differencesDp,
            /** Two fixed cells */
            cells = StaggeredCells.Fixed(2),

            /** 20 Dp of padding horizontally */
            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 10.dp),

            /** The content */
            content1 = {
                Timber.d("content for page: $page called")
                items(firstList) { photo ->
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .aspectRatio(photo.width!!.toFloat() / photo.height!!.toFloat())
                    ) {
                        GlideImage(
                            data = photo.urls.small ?: "",
                            blurHash = photo.blurHash ?: "",
                        )
                    }
                }
            },
            content2 = {
                items(secondList) { photo ->
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .aspectRatio(photo.width!!.toFloat() / photo.height!!.toFloat())
                    ) {
                        GlideImage(
                            data = photo.urls.small ?: "",
                            blurHash = photo.blurHash ?: "",
                        )
                    }
                }
            }
        )
    }
}