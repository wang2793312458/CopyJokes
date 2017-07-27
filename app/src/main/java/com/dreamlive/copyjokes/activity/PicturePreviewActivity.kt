package com.dreamlive.copyjokes.activity

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar

import com.dreamlive.copyjokes.R

class PicturePreviewActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var url: String
    var drawable: Drawable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_preview)
    }
}
