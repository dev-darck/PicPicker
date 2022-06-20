package com.project.settings_app.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.project.common_compos_ui.theme.appTypographyH3
import com.project.common_resources.R
import com.project.settings_app.BuildConfig.APP_NAME
import com.project.settings_app.BuildConfig.VERSION_APP

@Composable
fun Settings() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemGesturesPadding(),
    ) { paddingValue ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValue,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item("AppInfo") {
                Card(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    elevation = 10.dp
                ) {
                    Column(
                        modifier = Modifier.wrapContentWidth().padding(10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = "launch icon",
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(
                            text = "Version app $VERSION_APP",
                            style = appTypographyH3
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(
                            text = "App name $APP_NAME",
                            style = appTypographyH3
                        )
                    }
                }
            }
        }
    }
}