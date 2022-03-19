package com.inksy.UI.Fragments

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.inksy.R
import com.inksy.Remote.Status
import com.inksy.UI.Activities.StartingActivity
import com.inksy.UI.ViewModel.PeopleView
import com.inksy.Utils.TinyDB
import com.inksy.databinding.FragmentPasswordEmailBinding


class Password_Email : Fragment() {
    lateinit var peopleView: PeopleView
    lateinit var tinyDB: TinyDB
    var token = ""
    lateinit var binding: FragmentPasswordEmailBinding
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
        binding = FragmentPasswordEmailBinding.inflate(layoutInflater)
        peopleView = ViewModelProvider(this)[PeopleView::class.java]
        peopleView.init()

        tinyDB = TinyDB(requireContext())
        token = tinyDB.getString("token").toString()

        binding.ivBack.setOnClickListener{
            findNavController().navigate(R.id.action_password_Email_to_numberVerify)
        }
        binding.button.setOnClickListener {
            if (binding.email.text.isNullOrEmpty()) {
                binding.email.error = "Email cannot be empty"
            } else if (!binding.email.text.isValidEmail()) {
                binding.email.error = "Email Address is not valid"
            } else {
                binding.loader.visibility = View.VISIBLE
                peopleView.forgotPassword(binding.email.text.toString(), token)
                    ?.observe(requireActivity()) {
                        binding.loader.visibility = View.VISIBLE
                        when (it?.status) {
                            Status.SUCCESS -> {
                                Toast.makeText(
                                    requireContext(),
                                    it?.data?.message,
                                    Toast.LENGTH_SHORT
                                ).show()

                                var bundle = Bundle()
                                bundle.putBoolean("forgetPassword", true)
                                bundle.putString("email", binding.email.text.toString())
                                findNavController().navigate(
                                    R.id.action_password_Email_to_fragmentOtp,
                                    bundle
                                )
                            }
                            Status.ERROR -> {
                                tinyDB.clear()

                                requireContext().startActivity(
                                    Intent(
                                        requireContext(),
                                        StartingActivity::class.java
                                    )
                                )
                                Toast.makeText(
                                    requireContext(),
                                    "Token Expired",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            Status.LOADING -> {}
                        }
                    }

            }
        }
        return binding.root
    }

    private fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


}