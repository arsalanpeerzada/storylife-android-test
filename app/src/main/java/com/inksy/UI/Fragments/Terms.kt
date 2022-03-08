package com.inksy.UI.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.inksy.UI.Constants
import com.inksy.UI.ViewModel.OthersView
import com.inksy.databinding.FragmentTermsBinding

class Terms : Fragment() {

    lateinit var binding: FragmentTermsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTermsBinding.inflate(layoutInflater)
        binding.ivback.setOnClickListener {
            val action = TermsDirections.actionTermsToOthersList()
            findNavController().navigate(action)
        }

        getData(binding)
        return binding.root
    }

    fun getData(binding: FragmentTermsBinding) {
        val sharedPreferences = requireContext().getSharedPreferences(
            Constants.APP_NAME,
            Context.MODE_PRIVATE
        )

        val token = sharedPreferences.getString("token", "")


        val othersView: OthersView =
            ViewModelProviders.of(requireActivity())[OthersView::class.java]
        othersView.init()
        othersView.terms(
            token
        )?.observe(requireActivity()) {

            if (it?.status == 1) {


                binding.textView16.text = it.data?.title
                binding.description.text = it.data?.description
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


}