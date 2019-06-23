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
import android.os.Environment
import android.support.v7.app.AlertDialog
import android.webkit.*

//import android.R


//import android.webkit.WebViewClient




class MainActivity : AppCompatActivity() {

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
        webUniverse.webViewClient = WebViewClient()

//        val webview= WebView(this)
//        setContentView(webview)



            btn_ir.setOnClickListener {
                var progressBar = progressBar
                var progressStatus = webUniverse.progress
                progressBar.progress = 0



                //start animacao btn
                btn_ir?.playAnimation()
                urlLoad = "https://" + edt_url.editableText.toString().trim()
//                webUniverse.loadUrl(urlLoad)
//
//                webUniverse.webChromeClient = object : WebChromeClient() {
//                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
//                        //it calls when progress changed
//                        progressBar.progress = newProgress
//                        super.onProgressChanged(view, newProgress)
//
//                        if (newProgress == 100) {
//                            //if progress completes, progressbar gets hidden
//                            progressBar.visibility = progressBar.visibility
//                        }
//
//                    }
//                }


                val handler: Handler = Handler()

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

                    handler.post(Runnable {
                        progressBar.progress = progressStatus
                        //mostra Progressbar
                        progressBar.visibility = ProgressBar.VISIBLE
                        webUniverse.loadUrl(urlLoad)
                        Log.d("url", webUniverse.loadUrl(urlLoad).toString())

                        //cancela animacao btn
                        if (progressBar.progress == 100){
                            btn_ir?.cancelAnimation()
                        }

                    })
                }).start()


            }


        button_back.setOnClickListener{
            webUniverse.goBack()
        }
        button_forward.setOnClickListener {
            webUniverse.goForward()
        }
    }


}

