package com.example.webviewpoc

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.tracing.trace
import com.example.webviewpoc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var webView: WebView
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?): Unit = trace("onCreate") {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addWebView()
        loadUrl()
    }

    private fun loadUrl() = trace("loadUrl") {
        webView.loadUrl("https://paytm.com")
        webView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                reportFullyDrawn()
                webView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

/*
        binding.webViewXml.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                }
            }

            loadUrl("https://paytm.com")
        }
*/
    }

    fun addWebView() = trace("addWebView") {
        // Create the new view programmatically
        trace("webViewInit") {
            webView = WebView(this@MainActivity)
        }
        val webViewId = 1000001
        webView.id = webViewId // Replace with a unique ID for your new view
        webView.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, 0) // Set height to 0dp

        // Add the new view to the ConstraintLayout
        binding.mainLayout.addView(webView)

        // Create a ConstraintSet to define constraints
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.mainLayout)

        // Define constraints for the new view
        constraintSet.connect(
            webView.id,
            ConstraintSet.TOP,
            R.id.toolbar, // ID of view1
            ConstraintSet.BOTTOM,
            0 // Set the vertical margin (in dp) between newView and view1 if needed
        )

        constraintSet.connect(
            webView.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID, // ID of view2
            ConstraintSet.BOTTOM,
            0 // Set the vertical margin (in dp) between newView and view2 if needed
        )

        // Apply the constraints
        constraintSet.applyTo(binding.mainLayout)
    }

    override fun onResume() = trace("onResume") {
        super.onResume()
    }

    override fun onDestroy() = trace("onDestroy") {
        super.onDestroy()
        webView.destroy()
    }
}