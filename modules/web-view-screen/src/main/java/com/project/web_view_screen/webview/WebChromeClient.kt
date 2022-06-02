package com.project.web_view_screen.webview

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView

class WebChromeClient : WebChromeClient() {

    private var callbackFile: ((FileChooserParams?) -> Array<Uri>?)? = null

    fun onShowFileChooser(callBackFile: ((FileChooserParams?) -> Array<Uri>?)?) {
        this.callbackFile = callBackFile
    }

    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?,
    ): Boolean {
        val result = callbackFile?.let { it(fileChooserParams) }
        return if (result == null) {
            false
        } else {
            filePathCallback?.onReceiveValue(result)
            true
        }
    }
}