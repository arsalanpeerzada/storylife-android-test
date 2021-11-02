package com.storylife.UI.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.storylife.UI.Adapter.ArtworkAdapter
import com.storylife.databinding.FragmentArtworkBinding

class Artwork : Fragment() {

    lateinit var binding: FragmentArtworkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentArtworkBinding.inflate(layoutInflater)

        binding.rvFeatured.adapter = ArtworkAdapter(requireContext())

        binding.rvBestsellers.adapter = ArtworkAdapter(requireContext())

        return binding.root
    }


}