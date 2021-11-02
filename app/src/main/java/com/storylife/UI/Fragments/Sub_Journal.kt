package com.storylife.UI.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.storylife.UI.Activities.ViewAll
import com.storylife.UI.Adapter.BookAdapter
import com.storylife.databinding.FragmentSubJournalBinding

class Sub_Journal : Fragment() {

    lateinit var binding: FragmentSubJournalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSubJournalBinding.inflate(layoutInflater)

        binding.rvFriends.adapter = BookAdapter(requireContext())
        binding.rvHealth.adapter = BookAdapter(requireContext())

        binding.seeall.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), ViewAll::class.java))
        }

        return binding.root
    }


}