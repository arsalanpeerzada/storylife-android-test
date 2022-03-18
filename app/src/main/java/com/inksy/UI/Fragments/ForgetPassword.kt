package com.inksy.UI.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.inksy.R
import com.inksy.databinding.FragmentForgetPasswordBinding

class ForgetPassword : Fragment() {

    lateinit var binding: FragmentForgetPasswordBinding
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
        binding = FragmentForgetPasswordBinding.inflate(layoutInflater)

        binding.layGetStarted.setOnClickListener {
            if (binding.password1.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show()
            } else if (binding.cpassword1.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Confirm password cannot be empty", Toast.LENGTH_SHORT).show()
            } else if (binding.cpassword1.text.toString() != binding.password1.text.toString()){
                Toast.makeText(requireContext(), "Passwords doesnt match", Toast.LENGTH_SHORT).show()

            }else {
                forgetpassword()
            }
        }

        return binding.root
    }

    private fun forgetpassword() {
        findNavController().navigate(R.id.action_forgetPassword2_to_login)
    }


}