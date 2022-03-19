package com.inksy.UI.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.inksy.R
import com.inksy.UI.Activities.ProfileActivity
import com.inksy.UI.Adapter.ChatAdapter
import com.inksy.UI.Constants
import com.inksy.Utils.TinyDB
import com.inksy.databinding.FragmentChatBinding


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

        var tinydb = TinyDB(requireContext())


        if (!tinydb.getString("avatar").isNullOrEmpty()) {
            Glide.with(requireContext()).load(Constants.BASE_IMAGE + tinydb.getString("avatar"))
                .placeholder(R.drawable.ic_empty_user)
                .into(binding.profile)
        }
        var list = arrayOf(
            "Regina Lobo",
            "Jason Nicholas",
            "Emma Watson",
            "Lisa Messi",
            "Dane William",
            "Regina Lobo",

            )


        binding.rvChat.visibility = View.GONE
        binding.layoutemptyChat.visibility = View.VISIBLE


        var chatAdapter = ChatAdapter(requireContext(), list)
        binding.rvChat.adapter = chatAdapter

        binding.profile.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }


        binding.search.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                openactivity()
                return@OnEditorActionListener true
            }
            false
        })

        return binding.root
    }

    private fun openactivity() {

//        requireContext().startActivity(
//            Intent(requireContext(), ViewAll::class.java).putExtra(
//                "activity",
//                Constants.peopleSearch
//            )
//        )

    }

    private fun performSearch() {
        binding.search.clearFocus()
        binding.search.text.clear()
        val input: InputMethodManager? =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input?.hideSoftInputFromWindow(binding.search.windowToken, 0)
        //...perform search
    }

}