package com.storylife.UI.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.storylife.UI.Adapter.DashboardApprovedAdapter
import com.storylife.databinding.FragmentDashboardPendingBinding

class DashboardPending : Fragment() {

    lateinit var binding: FragmentDashboardPendingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardPendingBinding.inflate(layoutInflater)

        binding.rvDashboardApproved.adapter = DashboardApprovedAdapter(requireContext())

        return binding.root
    }


}