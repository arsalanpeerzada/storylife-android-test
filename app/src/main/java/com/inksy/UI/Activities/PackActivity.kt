package com.inksy.UI.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.inksy.UI.Adapter.PackAdapter
import com.inksy.UI.Constants
import com.inksy.databinding.ActivityPackBinding

class PackActivity : AppCompatActivity() {

    lateinit var binding: ActivityPackBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var i = intent.getBooleanExtra("fromAdapter", false)

        if (i) {
            binding.slider.visibility = View.GONE
            binding.editTitle.visibility = View.GONE
            binding.sliderleft.visibility = View.GONE
            binding.sliderright.visibility = View.GONE
            binding.buyNow.visibility = View.VISIBLE
            binding.price.visibility = View.GONE
            binding.checked.visibility = View.GONE
            binding.price.text = "Pack Price : $2.50"

        } else {
            binding.slider.visibility = View.VISIBLE
            binding.editTitle.visibility = View.VISIBLE
            binding.sliderleft.visibility = View.VISIBLE
            binding.sliderright.visibility = View.VISIBLE
            binding.buyNow.visibility = View.GONE
            binding.price.visibility = View.VISIBLE
            binding.checked.visibility = View.VISIBLE
        }

        binding.rvPackTitle.adapter = PackAdapter(this@PackActivity)

        binding.editTitle.setOnClickListener {
            binding.title.isEnabled = !binding.title.isEnabled
            binding.title.requestFocus()
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
            this.finish()
        }
        binding.checked.setOnClickListener {
            startActivity(
                Intent(this, ArtistDashboard::class.java).putExtra(
                    "activity",
                    Constants.packActivity
                )
            )

            this.finish()
        }
        binding.buyNow.setOnClickListener {

            this.finish()
        }

    }
}