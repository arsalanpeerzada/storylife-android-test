package com.storylife.UI.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.storylife.databinding.FragmentOtpBinding


class FragmentOtp : Fragment() {

    lateinit var binding: FragmentOtpBinding
    private val args: FragmentOtpArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOtpBinding.inflate(layoutInflater)

        // val mobileNumber = args.mobilenumber
        binding.fragmentotptitle.setText("Enter the 6-digit code sent to you at 03002598199")

        binding.imageView2.setOnClickListener() {
            val action = FragmentOtpDirections.actionFragmentOtpToNumberVerify(
                "03002598199"
            )

            findNavController().navigate(action)
        }

        binding.otpView.setOtpCompletionListener {
          val action =  FragmentOtpDirections.actionFragmentOtpToLogin()

            findNavController().navigate(action)
        }

        return binding.root
    }


}