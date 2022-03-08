package com.inksy.UI.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.inksy.R
import com.inksy.UI.Constants
import com.inksy.databinding.ActivityContactFormBinding

class Contact_Form : AppCompatActivity() {
    lateinit var binding : ActivityContactFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener(){
            onBackPressed()
            this.finish()
        }
        binding.button.setOnClickListener() {
            if (binding.name1.text.isNullOrEmpty()) {
                binding.nameError.visibility = View.VISIBLE
            }
            if (binding.summary.text.isNullOrEmpty()) {
                binding.summary.error = getString(R.string.messageError)
            }

            if (!binding.name1.text.isNullOrEmpty() && !binding.summary.text.isNullOrEmpty()) {

                onBackPressed()
                this.finish()


//                val action = LoginDirections.actionLoginToBio()
//                findNavController().navigate(action)

            }
        }

    }
}