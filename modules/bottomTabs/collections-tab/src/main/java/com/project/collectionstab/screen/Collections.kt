package com.project.collectionstab.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.collection_model.CollectionModel
import com.project.collection_model.name
import com.project.collectionstab.viewmodel.CollectionState.Loading
import com.project.collectionstab.viewmodel.CollectionState.Success
import com.project.collectionstab.viewmodel.CollectionViewModel
import com.project.common_resources.R
import com.project.common_ui.tab.Point
import com.project.common_ui.tab.SizeProportion.MIDDLE

@Composable
fun Collections() {
    val viewModel: CollectionViewModel = hiltViewModel()
    viewModel.collections()
    when (val state = viewModel.collectionResult.collectAsState().value) {
        is Loading -> {

        }
        is Success -> {
            LazyList(state.result)
        }
    }
}

@Composable
private fun LazyList(result: List<CollectionModel>) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) { paddingValue ->
        LazyColumn(
            contentPadding = paddingValue,
            modifier = Modifier,
        ) {
            items(result.size, key = { result[it].id.orEmpty() }) {
                CardCollection(collectionModel = result[it])
            }
        }
    }
}

@Composable
private fun CardCollection(collectionModel: CollectionModel) {
    Column(
        Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
    ) {
        CollagePhoto(
            modifier = Modifier.padding(horizontal = 16.dp),
            collectionModel.previewPhotos
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = collectionModel.title.orEmpty(),
            style = typography.h6
        )
        Row(
            modifier = Modifier.padding(start = 20.dp),
            verticalAlignment = CenterVertically
        ) {
            Text(
                text = "${collectionModel.totalPhotos.toString()} ${stringResource(id = R.string.photos)}",
                style = typography.caption,
                color = colors.onSecondary
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Point(modifier = Modifier.size(15.dp),
                sizeProportion = MIDDLE,
                color = colors.onSecondary)
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = collectionModel.user.name(stringResource(id = R.string.curated_text)),
                style = typography.caption,
                color = colors.onSecondary
            )
        }
        Spacer(modifier = Modifier.padding(top = 10.dp))
        TagsBottom(
            contentPadding = PaddingValues(horizontal = 16.dp),
            tags = collectionModel.tags.orEmpty()
        )
    }
}

@Composable
@Preview
private fun PreviewCollection() {
}