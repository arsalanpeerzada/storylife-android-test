package com.inksy.UI.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.inksy.R
import com.inksy.databinding.FragmentSelectAudienceBinding
import com.inksy.databinding.TablayoutBinding

class Select_Audience : Fragment() {

    lateinit var binding: FragmentSelectAudienceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectAudienceBinding.inflate(layoutInflater)

        var v: TablayoutBinding = binding.include6

//        var tab1: TextView = v.findViewById(R.id.tab1)

        v.tab1.text = resources.getString(R.string.privacy)


        binding.button.setOnClickListener {
            requireActivity().finish()
        }
        binding.back.setOnClickListener {
            requireActivity().finish()
        }

        binding.optionSpecificPeople.setOnClickListener {
            val action = Select_AudienceDirections.actionSelectAudienceToSelectedAudience()
            findNavController().navigate(action)

        }
        binding.option4.setOnClickListener {
            val action = Select_AudienceDirections.actionSelectAudienceToSelectedAudience()
            findNavController().navigate(action)
        }


        return binding.root
    }


}