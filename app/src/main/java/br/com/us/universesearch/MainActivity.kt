package br.com.us.universesearch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
//import android.view.View
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_main.*
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



            btn_ir.setOnClickListener {
                var progressBar = progressBar
                var progressStatus = webUniverse.progress
                progressBar.progress = 0


                //start animacao btn
                btn_ir?.playAnimation()
                urlLoad = "http://" + edt_url.editableText.toString().trim()
    //            Log.d("urlLoad", urlLoad)
//                webUniverse.loadUrl(urlLoad)
//                Log.d("load", webUniverse.progress.toString())

                val handler: Handler = Handler()

                // comeco progressbar
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

                        //cancela animacao btn
                        if (progressBar.progress == 100){
                            btn_ir?.cancelAnimation()
                        }

                    })
                }).start()


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

