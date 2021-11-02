package com.storylife.UI.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.storylife.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (true) {
            Handler().postDelayed({

                startActivity(Intent(this@SplashActivity, StartingActivity::class.java))
                this.finish()
            }, 2000)
        }
    }

    override fun onBackPressed() {

    }
}