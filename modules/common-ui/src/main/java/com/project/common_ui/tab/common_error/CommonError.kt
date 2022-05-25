package com.project.common_ui.tab.common_error

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.common_resources.R

@Preview
@Composable
fun Error(retry: () -> Unit = {}) {
    val interactionSource = remember { MutableInteractionSource() }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .clickable(
                        role = Role.Button,
                        onClick = retry,
                        interactionSource = interactionSource,
                        indication = null
                    )
                    .size(42.dp)
                    .background(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colors.onSecondary
                    ),
                contentAlignment = Center
            ) {
                Icon(
                    tint = Color.White,
                    painter = painterResource(id = R.drawable.error_retry_icon),
                    contentDescription = stringResource(id = R.string.default_content_descriptions)
                )
            }
            Spacer(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(CenterHorizontally))
            Text(
                text = stringResource(id = R.string.error_message)
            )
        }
    }
}