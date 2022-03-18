package com.inksy.UI.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.inksy.Utils.TinyDB
import com.inksy.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({
            var tinyDB = TinyDB(this)
            var isLogin = tinyDB.getString("token")
            if (isLogin != "") {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                this.finish()
            } else {
                startActivity(Intent(this@SplashActivity, StartingActivity::class.java))
                this.finish()
            }

        }, 2000)


    }

    override fun onBackPressed() {

    }
}