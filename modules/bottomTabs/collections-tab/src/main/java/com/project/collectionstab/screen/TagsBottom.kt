package com.project.collectionstab.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.project.collection_model.Tags

@Composable
fun TagsBottom(
    modifier: Modifier = Modifier,
    tags: List<Tags>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyRow(
        contentPadding = contentPadding,
        modifier = modifier,
    ) {
        items(tags.size, key = { it }) {
            val tagName = tags[it].title
            if (!tagName.isNullOrEmpty()) {
                ButtonTag(tagName) {

                }
            }
        }
    }
}

@Composable
private fun ButtonTag(tagName: String, onClick: () -> Unit = { }) {
    Box(
        modifier = Modifier
            .selectable(
                role = Role.Button,
                onClick = onClick,
                selected = false,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            )
            .border(1.dp, color = colors.onSecondary, shape = RoundedCornerShape(20.dp)),
    ) {
        Text(
            text = tagName,
            style = typography.caption,
            modifier = Modifier.padding(
                horizontal = 18.dp,
                vertical = 8.dp
            )
        )
    }
    Spacer(modifier = Modifier.padding(end = 10.dp))
}