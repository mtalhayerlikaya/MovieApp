package com.example.movieapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideStatusBar()
    }

    private fun hideStatusBar(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            val decorView: View = this.window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
            decorView.systemUiVisibility = uiOptions
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            this.window.decorView.windowInsetsController?.hide(
                WindowInsets.Type.statusBars()
            )
        }
    }
}