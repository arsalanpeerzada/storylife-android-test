package com.inksy.UI.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inksy.UI.Activities.Doodle_Drawing
import com.inksy.UI.Adapter.DashboardApprovedAdapter
import com.inksy.UI.Constants
import com.inksy.Interfaces.iOnClickListerner
import com.inksy.databinding.FragmentDashboardApprovedBinding

class DashboardApproved : Fragment() {

    lateinit var binding: FragmentDashboardApprovedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardApprovedBinding.inflate(layoutInflater)

        binding.rvDashboardApproved.adapter =
            DashboardApprovedAdapter(requireContext(), object : iOnClickListerner {
                override fun onclick(position: Int) {
                    requireContext().startActivity(
                        Intent(
                            requireContext(),
                            Doodle_Drawing::class.java
                        ).putExtra("fragment", Constants.fragment_approved)
                    )
                }
            })

        return binding.root
    }


}