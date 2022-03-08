package com.inksy.UI.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.inksy.databinding.FragmentCreatejournalBinding

class createjournal : Fragment() {

    lateinit var binding: FragmentCreatejournalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreatejournalBinding.inflate(layoutInflater)

        binding.ivBack.setOnClickListener {
            requireActivity().finish()
        }

        binding.emptyjournal.setOnClickListener {
            val action = createjournalDirections.actionCreatejournalToCreateJournalBackgroundBorderColor()
            findNavController().navigate(action)
        }
        return binding.root
    }


}