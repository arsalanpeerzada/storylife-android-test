package com.storylife.UI.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.storylife.UI.Activities.MainActivity
import com.storylife.databinding.FragmentBioBinding

class Bio : Fragment() {

    lateinit var binding: FragmentBioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentBioBinding.inflate(layoutInflater)


        binding.button.setOnClickListener() {
            context?.startActivity(Intent(requireContext(), MainActivity::class.java))

        }

        return binding.root
    }


}