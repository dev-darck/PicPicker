package com.project.collectionstab.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.project.collection_model.PreviewPhotos
import com.project.collection_model.smallPhoto
import com.project.image_loader.CoilImage

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
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when {
            count >= TripleImage -> MaxImage(previewPhotos)
            count >= DoubleImage -> MiddleCount(previewPhotos)
            count >= SingleImage -> SingleImage(previewPhotos)
        }
    }
}

@Composable
private fun RowScope.MaxImage(previewPhotos: List<PreviewPhotos>) {
    CoilImage(
        data = previewPhotos.smallPhoto(0),
        modifier = Modifier
            .padding(end = SpacingBetweenVertical)
            .weight(2F),
        shapes = RoundedCornerShape(
            bottomStart = RoundCorner,
            topStart = RoundCorner,
        ),
    )
    Column(modifier = Modifier.weight(1F)) {
        CoilImage(
            data = previewPhotos.smallPhoto(1),
            modifier = Modifier
                .padding(bottom = SpacingBetweenHorizontal)
                .weight(0.5F),
            shapes = RoundedCornerShape(
                topEnd = RoundCorner
            ),
        )
        CoilImage(
            data = previewPhotos.smallPhoto(2),
            modifier = Modifier.weight(0.5F),
            shapes = RoundedCornerShape(
                bottomEnd = RoundCorner,
            ),
        )
    }
}

@Composable
private fun RowScope.MiddleCount(previewPhotos: List<PreviewPhotos>) {
    CoilImage(
        data = previewPhotos.smallPhoto(0),
        modifier = Modifier
            .padding(end = 2.dp)
            .weight(1F),
        shapes = RoundedCornerShape(
            bottomStart = 20.dp,
            topStart = 20.dp,
        ),
    )
    CoilImage(
        data = previewPhotos.smallPhoto(1),
        modifier = Modifier
            .weight(1F),
        shapes = RoundedCornerShape(
            bottomEnd = 20.dp,
            topEnd = 20.dp,
        ),
    )
}

@Composable
private fun RowScope.SingleImage(previewPhotos: List<PreviewPhotos>) {
    CoilImage(
        data = previewPhotos.smallPhoto(0),
        modifier = Modifier,
        shapes = RoundedCornerShape(20.dp),
    )
}

@Composable
internal fun ShimmeringCollagePhoto(brush: Brush) {
    Spacer(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .height(253.dp)
            .fillMaxWidth()
            .background(brush, shape = RoundedCornerShape(20.dp))
    )
}