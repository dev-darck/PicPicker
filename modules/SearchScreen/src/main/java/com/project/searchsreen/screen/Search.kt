package com.project.searchsreen.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.project.common_resources.R
import com.project.common_ui.textField.SearchTextField
import com.project.searchsreen.viewmodel.SearchViewModel

@Composable
fun Search(viewModel: SearchViewModel) {
    var titleSelection by remember { mutableStateOf(TitleSelection.PROFILE) }
    var initQuery by remember { mutableStateOf(viewModel.query) }

    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.size(21.dp))
        SearchTextField(
            textInit = initQuery,
            modifier = Modifier,
            hint = stringResource(R.string.search_hint, stringResource(titleSelection.title).lowercase()),
        ) {
            if (it.isEmpty()) {
                initQuery = it
            }
            viewModel.search(it, titleSelection)
        }
        Spacer(modifier = Modifier.size(10.dp))
        SearchSelection(titleSelection) { selection ->
            titleSelection = selection
        }
    }
}