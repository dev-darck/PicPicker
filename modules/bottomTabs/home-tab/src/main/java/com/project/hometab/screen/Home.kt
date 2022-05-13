package com.project.hometab.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Preview
@Composable
fun Home() {
    val viewModel: HomeViewModel = hiltViewModel()
    val text = viewModel.state
    Text(text = text)
}