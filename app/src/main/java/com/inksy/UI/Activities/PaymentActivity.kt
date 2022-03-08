package com.inksy.UI.Activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.inksy.R
import com.inksy.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)


        setContentView(binding.root)
        binding.ivBack.setOnClickListener {
            onBackPressed()
            this.finish()
        }

        binding.paypal.setOnClickListener {

            Log.d("--->", "PayPal Clicked")
            binding.paypal.setImageDrawable(resources.getDrawable(R.drawable.paypal_tap))
            binding.venmo.setImageDrawable(resources.getDrawable(R.drawable.venmo))
        }
        binding.venmo.setOnClickListener {

            Log.d("-->", "Vemno Clicked")
            binding.paypal.setImageDrawable(resources.getDrawable(R.drawable.paypal))
            binding.venmo.setImageDrawable(
                resources.getDrawable(
                    R.drawable.venmo_tap

                )
            )
        }

        binding.button.setOnClickListener() {
            onBackPressed()
            this.finish()
        }
    }
}