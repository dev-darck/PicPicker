package com.project.common_ui.textField

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.project.common_compos_ui.theme.GrayMedium
import com.project.common_resources.R

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    textInit: String = "",
    hint: String,
    onValueChange: (String) -> Unit = {}
) = BaseTextField(
    modifier = modifier,
    textInit,
    label = "",
    onValueChange = onValueChange
) { showHint, innerTextField ->
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        Box(
            modifier = modifier.weight(1F, true),
            contentAlignment = Alignment.CenterStart,
        ) {
            if (showHint && textInit.isEmpty()) {
                Hint(hint)
            }
            innerTextField()
        }

        Spacer(modifier = Modifier.size(10.dp))
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.shearch_icon),
            tint = GrayMedium,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.size(10.dp))
    }
}

@Composable
fun TextField(hint: String, label: String, onValueChange: (String) -> Unit = {}) =
    BaseTextField(modifier = Modifier, label = label, onValueChange = onValueChange) { showHint, innerTextField ->
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth(),
        ) {
            if (showHint) {
                Hint(hint)
            }
            innerTextField()
        }
    }

@Composable
fun PasswordTextField(
    hint: String,
    label: String,
    onValueChange: (String) -> Unit = {},
    forgotPassword: () -> Unit = {}
) = BaseTextField(modifier = Modifier, label = label, onValueChange = onValueChange) { showHint, innerTextField ->
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize(),
    ) {
        if (showHint) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Hint(hint, Modifier.weight(1f))
                Forgot(forgotPassword)
            }
        }
        innerTextField()
    }
}

@Composable
private fun Forgot(forgotPassword: () -> Unit) = Text(
    text = "FORGOT?",
    style = MaterialTheme.typography.overline,
    color = MaterialTheme.colors.secondary,
    modifier = Modifier.clickable { forgotPassword() }
)

@Composable
private fun Hint(text: String, modifier: Modifier = Modifier) = Text(
    text = text,
    style = MaterialTheme.typography.h3,
    color = MaterialTheme.colors.onSecondary,
    modifier = modifier
)



