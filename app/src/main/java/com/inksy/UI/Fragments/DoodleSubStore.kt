package com.inksy.UI.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inksy.UI.Adapter.DoodleAdapter
import com.inksy.databinding.FragmentDoodleStoreBinding


class DoodleSubStore : Fragment() {

    lateinit var binding: FragmentDoodleStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDoodleStoreBinding.inflate(layoutInflater)

        binding.rvDoodle.adapter = DoodleAdapter(requireContext())

        return binding.root
    }


}