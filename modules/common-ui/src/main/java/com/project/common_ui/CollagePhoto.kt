package com.project.common_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.project.image_loader.GlideImage
import com.project.image_loader.ImageSize
import com.project.model.PreviewPhotos
import com.project.model.blurHash
import com.project.model.regularPhoto
import com.project.model.smallPhoto

private const val TripleImage = 3
private const val DoubleImage = 2
private const val SingleImage = 1
private val DefaultHeight = 253.dp
private val RoundCorner = 20.dp
private val SpacingBetweenVertical = 2.dp
private val SpacingBetweenHorizontal = 2.dp

@Composable
fun CollagePhoto(
    modifier: Modifier = Modifier,
    previewPhotos: List<PreviewPhotos>?,
    height: Dp = DefaultHeight,
) {
    if (previewPhotos.isNullOrEmpty()) return
    val count = previewPhotos.size
    Row(
        modifier = modifier
            .height(height)
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when {
            count >= TripleImage -> MaxImage(previewPhotos, height)
            count >= DoubleImage -> MiddleCount(previewPhotos, height)
            count >= SingleImage -> SingleImage(previewPhotos, height)
        }
    }
}

@Composable
private fun RowScope.MaxImage(previewPhotos: List<PreviewPhotos>, height: Dp) {
    GlideImage(
        data = previewPhotos.regularPhoto(0),
        imageSize = ImageSize(_height = height),
        modifier = Modifier
            .padding(end = SpacingBetweenVertical)
            .height(height)
            .weight(2F),
        shapes = RoundedCornerShape(
            bottomStart = RoundCorner,
            topStart = RoundCorner,
        ),
        blurHash = previewPhotos.blurHash(0)
    )
    Column(modifier = Modifier.weight(1F)) {
        val smallHeight = height / 2
        GlideImage(
            data = previewPhotos.smallPhoto(1),
            imageSize = ImageSize(_height = smallHeight),
            modifier = Modifier
                .padding(bottom = SpacingBetweenHorizontal)
                .height(smallHeight)
                .weight(.5F),
            shapes = RoundedCornerShape(
                topEnd = RoundCorner
            ),
            blurHash = previewPhotos.blurHash(1)
        )
        GlideImage(
            data = previewPhotos.smallPhoto(2),
            imageSize = ImageSize(_height = smallHeight),
            modifier = Modifier
                .height(smallHeight)
                .weight(0.5F),
            shapes = RoundedCornerShape(
                bottomEnd = RoundCorner,
            ),
            blurHash = previewPhotos.blurHash(2)
        )
    }
}

@Composable
private fun RowScope.MiddleCount(previewPhotos: List<PreviewPhotos>, height: Dp) {
    GlideImage(
        data = previewPhotos.regularPhoto(0),
        imageSize = ImageSize(_height = height),
        modifier = Modifier
            .height(height)
            .padding(end = 2.dp)
            .weight(1F),
        shapes = RoundedCornerShape(
            bottomStart = 20.dp,
            topStart = 20.dp,
        ),
        blurHash = previewPhotos.blurHash(0)
    )
    GlideImage(
        data = previewPhotos.regularPhoto(1),
        imageSize = ImageSize(_height = height),
        modifier = Modifier
            .height(height)
            .weight(1F),
        shapes = RoundedCornerShape(
            bottomEnd = 20.dp,
            topEnd = 20.dp,
        ),
        blurHash = previewPhotos.blurHash(1)
    )
}

@Composable
private fun SingleImage(previewPhotos: List<PreviewPhotos>, height: Dp) {
    GlideImage(
        data = previewPhotos.regularPhoto(0),
        imageSize = ImageSize(_height = height),
        modifier = Modifier.height(height),
        shapes = RoundedCornerShape(20.dp),
        blurHash = previewPhotos.blurHash(1)
    )
}

@Composable
fun ShimmeringCollagePhoto(brush: Brush) {
    Spacer(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .height(253.dp)
            .fillMaxWidth()
            .background(brush, shape = RoundedCornerShape(20.dp))
    )
}