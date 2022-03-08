package com.inksy.UI.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.inksy.UI.Adapter.AudienceAdapter
import com.inksy.databinding.FragmentSelectedAudienceBinding


class SelectedAudience : Fragment() {

    lateinit var binding: FragmentSelectedAudienceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectedAudienceBinding.inflate(layoutInflater)

        binding.back.setOnClickListener() {
            val action = SelectedAudienceDirections.actionSelectedAudienceToSelectAudience()
            findNavController().navigate(action)
        }

        binding.button.setOnClickListener {
            val action = SelectedAudienceDirections.actionSelectedAudienceToSelectAudience()
            findNavController().navigate(action)
        }


        var list = arrayOf(
            "Regina Lobo",
            "Jason Nicholas",
            "Emma Watson",
            "Lisa Messi",
            "Dane William",
            "Regina Lobo",

            )
        binding.rvAudience.adapter = AudienceAdapter(requireContext(), list)
        return binding.root
    }

}