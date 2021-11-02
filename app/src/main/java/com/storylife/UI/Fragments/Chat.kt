package com.storylife.UI.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.storylife.UI.Activities.ProfileActivity
import com.storylife.UI.Adapter.ChatAdapter
import com.storylife.databinding.FragmentChatBinding


class Chat : Fragment() {

    lateinit var binding: FragmentChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(layoutInflater)

        var chatAdapter = ChatAdapter(requireContext())
        binding.rvChat.adapter = chatAdapter

        binding.profile.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }

        return binding.root
    }

}