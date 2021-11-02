package com.storylife.UI.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.storylife.UI.Adapter.ArtworkAdapter
import com.storylife.UI.Adapter.BookAdapter
import com.storylife.databinding.ActivityPeopleBinding
import com.storylife.databinding.FragmentProfileBinding

class People : AppCompatActivity() {

    lateinit var binding: ActivityPeopleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =ActivityPeopleBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.rvFriends.adapter = BookAdapter(this)

        binding.rvHealth.adapter = ArtworkAdapter(this)

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

    }
}