package com.project.web_view_screen.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.project.navigationapi.config.WebViewRoute.TypeUrl.UNKNOWN
import com.project.web_view_screen.viewmodel.WebViewViewModel
import com.project.web_view_screen.webview.CustomWebView

@Composable
fun WebView(viewModel: WebViewViewModel) {
    val url = viewModel.url
    when (viewModel.type) {
        UNKNOWN.type -> {

        }
        else -> {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = {
                    CustomWebView(it).apply {
                        loadUrl(url)
                    }
                }
            )
        }
    }
}