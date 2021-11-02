package com.storylife.UI.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.storylife.UI.Adapter.JournalAdapter
import com.storylife.databinding.FragmentSubPeopleBinding

class Sub_People : Fragment() {

    lateinit var binding: FragmentSubPeopleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSubPeopleBinding.inflate(layoutInflater)

        binding.rvChat.adapter = JournalAdapter(requireContext())
        return binding.root
    }


}