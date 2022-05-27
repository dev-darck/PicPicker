package com.project.bottomsheet_image_piker

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.project.image_loader.CoilImage
import com.project.image_loader.ImageSize
import kotlin.math.roundToInt

val ImageSize = 100.dp

@Composable
fun ImagePikerBottomSheet(viewModel: ImagePikerViewModel) {

    val imageList = viewModel.images.collectAsState(initial = emptyList()).value

    val count = (LocalConfiguration.current.screenWidthDp / ImageSize.value).roundToInt()
    var permission by remember {
        mutableStateOf(false)
    }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permission = isGranted
    }

    if (permission) {
        LaunchedEffect(key1 = imageList) {
            viewModel.getImageFromGallery()
        }
    }

    Button(onClick = {
        launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }) {
        Text(text = "GO")
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(count),
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(imageList) {
            CoilImage(
                data = it.path.orEmpty(),
                modifier = Modifier.size(ImageSize),
                size = ImageSize(ImageSize, ImageSize),
                contentScale = ContentScale.Crop
            )
        }
    }
}