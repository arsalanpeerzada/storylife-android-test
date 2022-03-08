package com.inksy.UI.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inksy.UI.Adapter.AmountReceivedAdapter
import com.inksy.databinding.ActivityAmountReceivedBinding

class AmountReceived : AppCompatActivity() {



    lateinit var binding: ActivityAmountReceivedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAmountReceivedBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.rvFriends.adapter = AmountReceivedAdapter(this)
        binding.back.setOnClickListener {
            onBackPressed()
            this.finish()
        }
    }
}