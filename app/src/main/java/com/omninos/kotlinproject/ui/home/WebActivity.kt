package com.omninos.kotlinproject.ui.home

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.omninos.kotlinproject.R
import com.omninos.kotlinproject.util.hide
import com.omninos.kotlinproject.util.show
import kotlinx.android.synthetic.main.activity_main.*

class WebActivity : AppCompatActivity() {

    var mywebview: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        mywebview = findViewById(R.id.webView)
        val settings = mywebview?.settings

        settings?.javaScriptEnabled = true

        settings?.setAppCacheEnabled(true)
        settings?.cacheMode = WebSettings.LOAD_DEFAULT
        settings?.setAppCachePath(cacheDir.path)

        settings?.blockNetworkImage = false
        // Whether the WebView should load image resources
        settings?.loadsImagesAutomatically = true


        // More web view settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            settings?.safeBrowsingEnabled = true  // api 26
        }
        //settings.pluginState = WebSettings.PluginState.ON
        settings?.useWideViewPort = true
        settings?.loadWithOverviewMode = true
        settings?.javaScriptCanOpenWindowsAutomatically = true
        settings?.mediaPlaybackRequiresUserGesture = false


        // More optional settings, you can enable it by yourself
        settings?.domStorageEnabled = true
        settings?.setSupportMultipleWindows(true)
        settings?.loadWithOverviewMode = true
        settings?.allowContentAccess = true
        settings?.setGeolocationEnabled(true)
        settings?.allowUniversalAccessFromFileURLs = true
        settings?.allowFileAccess = true

        // WebView settings
        mywebview?.fitsSystemWindows = true


        mywebview?.setLayerType(View.LAYER_TYPE_HARDWARE, null)


        // Set web view client
        mywebview?.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                progress_circular.show()

            }

            override fun onPageFinished(view: WebView, url: String) {
                progress_circular.hide()
            }
        }

        // Set web view chrome client
        mywebview?.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
//                progress_bar.progress = newProgress
            }
        }

        mywebview!!.loadUrl(intent.getStringExtra("Url"))

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (mywebview!!.canGoBack()) {
            // Go to back history
            mywebview?.goBack()
        }
    }
}
