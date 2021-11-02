package com.storylife.UI.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.storylife.UI.Activities.ArtisePanel
import com.storylife.UI.Activities.ArtistDashboard
import com.storylife.UI.Activities.DoodleStore
import com.storylife.databinding.FragmentMoreInfoBinding

class MoreInfo : Fragment() {


    lateinit var binding: FragmentMoreInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoreInfoBinding.inflate(layoutInflater)

        binding.tvDoodle.setOnClickListener() {
            openNewActivity(DoodleStore::class.java)
        }
        binding.imageDooble.setOnClickListener { openNewActivity(DoodleStore::class.java) }
        binding.tvDescDoodle.setOnClickListener { openNewActivity(DoodleStore::class.java) }

        binding.tvArtist.setOnClickListener { openNewActivity(ArtisePanel::class.java) }
        binding.imgArtist.setOnClickListener { openNewActivity(ArtisePanel::class.java) }
        binding.subtvArtist.setOnClickListener { openNewActivity(ArtisePanel::class.java) }





        return binding.root

    }

    private fun openNewActivity(clazz: Class<*>) {
        requireContext().startActivity(Intent(requireContext(), clazz))
    }


}