package com.storylife.UI.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.storylife.UI.Adapter.JournalAdapter
import com.storylife.databinding.ActivityViewAllBinding

class ViewAll : AppCompatActivity() {

    lateinit var binding: ActivityViewAllBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllBinding.inflate(layoutInflater)

        binding.rvAll.adapter = JournalAdapter(this@ViewAll)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }
    }
}