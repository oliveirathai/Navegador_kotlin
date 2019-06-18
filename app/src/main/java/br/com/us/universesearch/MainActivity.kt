package br.com.us.universesearch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var url = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webUniverse.settings.javaScriptEnabled = true
        webUniverse.settings.setSupportZoom(true)
        webUniverse.settings.builtInZoomControls = true
        webUniverse.settings.displayZoomControls = true
        webUniverse.webViewClient = WebViewClient()

        btn_ir.setOnClickListener {

            url = "http://" + edt_url.editableText.toString().trim()
            Log.d("url", url)
            webUniverse.loadUrl(url)

        }
        button_back.isEnabled = webUniverse.canGoBack()
        button_forward.isEnabled = webUniverse.canGoForward()

        button_back.setOnClickListener{
            webUniverse.goBack()
        }
        button_forward.setOnClickListener {
            webUniverse.goForward()
        }
    }



}
