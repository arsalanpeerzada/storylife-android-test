package com.storylife.UI.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.storylife.UI.Adapter.DashboardApprovedAdapter
import com.storylife.databinding.FragmentDashboardApprovedBinding

class Dashboard_Approved : Fragment() {

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

        binding.rvDashboardApproved.adapter = DashboardApprovedAdapter(requireContext())

        return binding.root
    }


}