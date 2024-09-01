package com.example.rickandmortyapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.load
import coil.request.CachePolicy

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)
        val imageLoader = ImageLoader.Builder(this)
            .components {
                add(GifDecoder.Factory())
            }
            .build()
        Coil.setImageLoader(imageLoader)
        val splashGifView = findViewById<ImageView>(R.id.splashGifView)
        splashGifView.load(R.drawable.rickmorty_splash) { // Use R.raw if placed in raw directory
            crossfade(true)
            diskCachePolicy(CachePolicy.DISABLED) // Optional: Disable disk caching
        }

        splashGifView.load(R.drawable.rickmorty_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close SplashScreenActivity
        }, 2000) // Delay for 3 seconds
    }
}