package me.eric.view

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import me.eric.progress.XProgressView

class MainActivity : AppCompatActivity() {
    var et_progress : EditText ? = null
    var progressView : XProgressView ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_progress = findViewById(R.id.et_progress)
        progressView = findViewById(R.id.XProgressView)
    }

    fun btn_progress(view: View) {
        et_progress!!.text.toString().toFloat().apply {
            progressView!!.setProgress(this)
        }
    }
}