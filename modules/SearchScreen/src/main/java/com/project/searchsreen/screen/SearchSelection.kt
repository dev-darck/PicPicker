package com.project.searchsreen.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.project.common_resources.R

enum class TitleSelection(@StringRes val title: Int) {
    PROFILE(title = R.string.search_profile),
    PHOTOS(title = R.string.search_photos),
    COLLECTIONS(title = R.string.search_collections);

    companion object {
        fun size(): Int = TitleSelection.values().size
        fun getByIndex(index: Int): TitleSelection = TitleSelection.values()[index]
    }
}

data class SelectionTabModel(
    @StringRes val title: Int,
    val isSelection: Boolean = false,
    val onClick: () -> Unit = {},
)

@Composable
fun SearchSelection(toSelection: TitleSelection, onSelection: (TitleSelection) -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(TitleSelection.size()) {
            val titleSelection = TitleSelection.getByIndex(it)
            SelectionTab(SelectionTabModel(
                title = titleSelection.title,
                isSelection = titleSelection == toSelection,
                onClick = {
                    onSelection(titleSelection)
                }
            ), modifier = Modifier.weight(1F))
        }
    }
}

@Composable
private fun SelectionTab(selectionTabModel: SelectionTabModel, modifier: Modifier) {
    Spacer(modifier = Modifier.padding(end = 10.dp))
    val colorSelect =
        if (selectionTabModel.isSelection) MaterialTheme.colors.onBackground else MaterialTheme.colors.onSecondary
    Box(
        modifier = modifier
            .width(111.dp)
            .selectable(
                role = Role.Tab,
                onClick = selectionTabModel.onClick,
                selected = false,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            )
            .border(1.dp, color = colorSelect, shape = RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(selectionTabModel.title),
            maxLines = 1,
            style = MaterialTheme.typography.h4.copy(color = colorSelect),
            modifier = Modifier.padding(
                vertical = 8.dp
            ).align(Alignment.Center)
        )
    }
    Spacer(modifier = Modifier.padding(end = 10.dp))
}
