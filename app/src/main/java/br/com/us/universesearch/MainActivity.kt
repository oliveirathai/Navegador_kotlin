package br.com.us.universesearch

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
//import android.view.View
import android.widget.ProgressBar
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.webkit.*
import android.widget.TextView

//import android.R


//import android.webkit.WebViewClient


class MainActivity : AppCompatActivity() {
    private val logTag = "MainActivity"

    lateinit var animation: LottieAnimationView
    lateinit var webClient: WebChromeClient
    var urlLoad = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webUniverse.settings.javaScriptEnabled = true
        webUniverse.settings.setSupportZoom(true)
        webUniverse.settings.builtInZoomControls = true
        webUniverse.settings.displayZoomControls = false

//        val webview= WebView(this)
//        setContentView(webview)

        webUniverse.webViewClient = object : WebViewClient() {

            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse?
            ) {
                Log.d(logTag, "onReceivedHttpError(view: $view, request: $request, errorResponse: $errorResponse) ")

                super.onReceivedHttpError(view, request, errorResponse)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                Log.d(logTag, "onReceivedHttpError(request: $request, error: $error) ")
                Log.d(logTag, "onReceivedHttpError(url: ${request?.url}, code: ${error?.errorCode}) ")
                Log.d(logTag, "onReceivedHttpError(method: ${request?.method}, description: ${error?.description}) ")
                Log.d(logTag, "onReceivedHttpError(method: ${request?.method}, ERROR_CONNECT: ${ERROR_CONNECT}) ")

                when (error?.errorCode) {
                    ERROR_CONNECT -> {
                        val url = request?.url?.toString()?: ""
                        Log.d(logTag, "onReceivedHttpError(url: $url, ERROR_CONNECT: ${ERROR_CONNECT}) ")

                        if (url.startsWith("http://")) {
                            urlLoad = url.replace("http://", "https://")
                            edt_url.setText(urlLoad, TextView.BufferType.EDITABLE)
                            progressBar.visibility = ProgressBar.INVISIBLE
                            Log.d(logTag, "onReceivedHttpError(Reload HTTPS: $urlLoad, ERROR_CONNECT: ${ERROR_CONNECT}) ")
                            webUniverse.loadUrl(urlLoad)
                        }
                        if (url.startsWith("https://")) {
                            urlLoad = url.replace("https://", "http://")
                            edt_url.setText(urlLoad, TextView.BufferType.EDITABLE)
                            progressBar.visibility = ProgressBar.INVISIBLE
                            Log.d(logTag, "onReceivedHttpError(Reload HTTP: $urlLoad, ERROR_CONNECT: ${ERROR_CONNECT}) ")
                            webUniverse.loadUrl(urlLoad)
                        }
                    }
                }

                super.onReceivedError(view, request, error)
            }

        }

        btn_ir.setOnClickListener {
            var progressBar = progressBar
            var progressStatus = webUniverse.progress
            progressBar.progress = 0
            urlLoad = edt_url.editableText.toString().trim()


            //start animacao btn
            btn_ir?.playAnimation()
            if (!urlLoad.startsWith("https://") && !urlLoad.startsWith("http://")) {
                urlLoad = "http://$urlLoad"
            }
//                webUniverse.loadUrl(urlLoad)
//


//
            val handler = Handler()

//                 comeco progressbar
            Thread(Runnable {
                while (progressStatus < 100)
                    progressStatus += 1
                //delay progressbar
                try {
                    Thread.sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                handler.post {
                    progressBar.progress = progressStatus
                    //mostra Progressbar
                    progressBar.visibility = ProgressBar.VISIBLE
                    webUniverse.loadUrl(urlLoad)
                    Log.d(logTag, "StartLoad: $urlLoad")

                    //cancela animacao btn
                    if (progressBar.progress == 100) {
//                        progressBar.visibility = ProgressBar.INVISIBLE
                        btn_ir?.cancelAnimation()
                    }

                }
            }).start()


        }



        button_back.setOnClickListener {
            webUniverse.goBack()
        }
        button_forward.setOnClickListener {
            webUniverse.goForward()
        }
    }


}

