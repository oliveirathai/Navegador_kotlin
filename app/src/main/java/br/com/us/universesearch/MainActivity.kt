package br.com.us.universesearch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var url = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_ir.setOnClickListener {
            url = edt_url.editableText.toString().trim()

        }
            webUniverse.settings.javaScriptEnabled = true
            webUniverse.loadUrl(url)
    }
}
