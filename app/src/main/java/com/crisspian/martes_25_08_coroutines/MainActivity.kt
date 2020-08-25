package com.crisspian.martes_25_08_coroutines

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val url = "https://apod.nasa.gov/apod/image/1908/M61-HST-ESO-S1024.jpg"

        CoroutineScope(Dispatchers.Main).launch {
            val image = doInBackground(url)
            Log.d("COROUTINE", image.toString())
            if (image != null) {
                updateView(image)
            }
        }

    }


    suspend fun doInBackground(url: String): Bitmap? {
        var bmp: Bitmap? = null
        withContext(Dispatchers.Default) {
            try {
                progressBar.visibility = View.VISIBLE
                val newURL = URL(url)
                val inputStream = newURL.openConnection().getInputStream()
                Log.e("INPUT", inputStream.toString())
                bmp = BitmapFactory.decodeStream(newURL.openConnection().getInputStream())

            } catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
                e.printStackTrace()
            }
        }
        return bmp
    }

    fun updateView(result: Bitmap) {
        progressBar.visibility = View.GONE
        imgOfDay.setImageBitmap(result)
    }


}