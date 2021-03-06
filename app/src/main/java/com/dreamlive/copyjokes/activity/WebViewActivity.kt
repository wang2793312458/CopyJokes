package com.dreamlive.copyjokes.activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.webkit.WebSettings
import com.dreamlive.copyjokes.R
import com.dreamlive.jokes.views.ProgressWebView

class WebViewActivity : AppCompatActivity() {
    lateinit var progressWebWebview: ProgressWebView
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        initView()
    }

    private fun initView() {
        progressWebWebview = findViewById(R.id.progress_web_webview) as ProgressWebView
        toolbar = findViewById(R.id.toolbar) as Toolbar

        val intent = intent
        if (intent != null) {
            val title: String = intent.getStringExtra("title")
            val url: String = intent.getStringExtra("url")
            toolbar.title = title
            progressWebWebview.settings.javaScriptEnabled = true
            progressWebWebview.settings.cacheMode = WebSettings.LOAD_DEFAULT
            progressWebWebview.loadUrl(url)
        }
        setSupportActionBar(toolbar)
        val actionBar: ActionBar = supportActionBar as ActionBar
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
