package com.inksy.UI.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inksy.UI.Adapter.NotificationAdapter
import com.inksy.databinding.FragmentNotificationsBinding
import com.inksy.UI.Activities.List


class Notifications_Fragment : Fragment() {

    lateinit var binding: FragmentNotificationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationsBinding.inflate(layoutInflater)

        var list = arrayOf(
            "Regina Lobo",
            "Jason Nicholas",
            "Emma Watson",
            "Lisa Messi",
            "Dane William",
            "Regina Lobo",

            )


        binding.rvNotifications.visibility = View.GONE
        binding.layoutemptyNotifications.visibility = View.VISIBLE

        binding.rvNotifications.adapter = NotificationAdapter(requireContext(), list)

        binding.followRequests.setOnClickListener {
            startActivity(
                Intent(requireContext(), List::class.java).putExtra(
                    "followRequests",
                    true
                )
            )
        }

        return binding.root
    }


}