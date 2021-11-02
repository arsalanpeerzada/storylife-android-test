package com.storylife.UI.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.storylife.R
import com.storylife.databinding.FragmentLoginBinding

class Login : Fragment() {

    lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)



        binding.button.setOnClickListener() {

            if (binding.edtName.isEmpty()) {
                binding.edtName.hint = getString(R.string.ErrorEmail)

            }
            if (binding.password.isEmpty()) {
                binding.password.hint = getString(R.string.ErrorPassword)
            }

            if (!binding.edtName.isEmpty() || !binding.password.isEmpty()) {
                val action = LoginDirections.actionLoginToBio()

                findNavController().navigate(action)

            }


        }


        return binding.root
    }


}