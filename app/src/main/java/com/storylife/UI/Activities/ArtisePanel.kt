package com.storylife.UI.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.storylife.databinding.ActivityArtisePanelBinding

class ArtisePanel : AppCompatActivity() {

    lateinit var binding: ActivityArtisePanelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtisePanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.iamin.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ArtistDashboard::class.java
                )
            )
        }

    }
}