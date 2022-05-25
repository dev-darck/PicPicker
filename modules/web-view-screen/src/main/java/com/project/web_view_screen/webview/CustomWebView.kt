package com.project.web_view_screen.webview

import android.content.Context
import android.net.Uri
import android.webkit.WebView
import timber.log.Timber

class CustomWebView(context: Context) : WebView(context) {

    private val client = WebChromeClient()

    init {
        webChromeClient = client
    }

    fun showFileChooser(callBackFile: (((android.webkit.WebChromeClient.FileChooserParams?) -> Array<Uri>?)?)) {
        client.onShowFileChooser(callBackFile)
    }

    override fun loadUrl(url: String) {
        Timber.i("Load Url -> $url")
        super.loadUrl(url)
    }
}