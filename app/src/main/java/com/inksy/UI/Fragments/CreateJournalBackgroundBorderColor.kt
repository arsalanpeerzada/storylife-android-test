package com.inksy.UI.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.inksy.Interfaces.iOnClickListerner
import com.inksy.R
import com.inksy.UI.Adapter.BorderColorAdapter
import com.inksy.databinding.FragmentCreateJournalBackgroundBoderColorBinding

class CreateJournalBackgroundBorderColor : Fragment(), iOnClickListerner {


    lateinit var binding: FragmentCreateJournalBackgroundBoderColorBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentCreateJournalBackgroundBoderColorBinding.inflate(layoutInflater)

        val array = arrayOf(
            resources.getColor(R.color.applightblue), resources.getColor(R.color.appBlueDark),
            resources.getColor(R.color.grey), resources.getColor(R.color.errorRed)
        )

        val array2 = arrayOf(
            resources.getColor(R.color.black), resources.getColor(R.color.purple_500),
        )

        binding.linearLayout.adapter = BorderColorAdapter(requireContext(), array, this);

        binding.linearLayout2.adapter = BorderColorAdapter(requireContext(), array2, this);

//        binding.linearLayout.setOnClickListener {
//            val action =
//                CreateJournalBackgroundBorderColorDirections.actionCreateJournalBackgroundBorderColorToLastEntry()
//            findNavController().navigate(action)
//        }

        binding.ivBack.setOnClickListener {
            val action =
                CreateJournalBackgroundBorderColorDirections.actionCreateJournalBackgroundBorderColorToCreatejournal()
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onclick(position: Int) {
        val action =
            CreateJournalBackgroundBorderColorDirections.actionCreateJournalBackgroundBorderColorToCreateJournalCoverInfo()
        findNavController().navigate(action)
    }
}