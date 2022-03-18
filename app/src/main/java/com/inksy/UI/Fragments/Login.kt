package com.inksy.UI.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.inksy.R
import com.inksy.UI.Activities.MainActivity
import com.inksy.UI.ViewModel.LoginView
import com.inksy.Utils.TinyDB
import com.inksy.databinding.FragmentLoginBinding


class Login : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var loginView: LoginView

    // private val args: FragmentOtpArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        val mobileNumber = arguments?.getString("number")

        val code = arguments?.getString("code")//args.mobileNumber
        binding.button.setOnClickListener() {

            if (binding.edtName1.text.isNullOrEmpty()) {
                binding.emailError.visibility = View.VISIBLE
            }
            if (binding.password1.text.isNullOrEmpty()) {
                binding.passwordError.visibility = View.VISIBLE
                binding.emailError.text = getString(R.string.emailError)
            }

            if (!binding.edtName1.text.isNullOrEmpty() && !binding.password1.text.isNullOrEmpty()) {

                if (binding.edtName1.text.isValidEmail()) {

                    loginView = ViewModelProvider(requireActivity())[LoginView::class.java]
                    loginView.init()
                    loginView.login(
                        binding.edtName1.text.toString(),
                        binding.password1.text.toString(),
                        mobileNumber!!,
                        code!!
                    )?.observe(requireActivity()) {

                        if (it?.status == 1) {

                            var email = it.data?.email
                            var password = binding.password1.text.toString()

                            var tinydb = TinyDB(requireContext())

                            tinydb.putString("password", password)
                            tinydb.putString("email", email)
                            tinydb.putString("id", it.data?.id.toString())
                            tinydb.putString("token", it.data?.token)
                            tinydb.putString("email", it.data?.email)
                            tinydb.putString("phone", it.data?.phone)
                            tinydb.putString("followers", it.data?.followerCount.toString())
                            tinydb.putString("following", it.data?.followingCount.toString())
                            tinydb.putString("points", it.data?.points.toString())
                            tinydb.putString("phonecode", it.data?.phoneCode.toString())
                            tinydb.putString("isprivate", it.data?.isPrivateProfile!!.toString())
                            tinydb.putInt("isprofilecompleted", it.data?.isProfileCompleted!!)

                            if (it.data?.isProfileCompleted == 0) {

                                var action: NavDirections = LoginDirections.actionLoginToBio()
                                findNavController().navigate(action)
                            } else {
                                tinydb.putString("fullname", it.data?.fullName)
                                tinydb.putString("bio", it?.data?.bio)


                                if (it?.data?.avatar.isNullOrBlank()) {

                                } else {
                                    tinydb.putString("avatar", it?.data?.avatar)
                                }

                                requireContext().startActivity(
                                    Intent(
                                        requireContext(),
                                        MainActivity::class.java
                                    )
                                )
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } else {
                            Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()

                            if (it?.message.toString() == "Your account is not activated."
                                || it?.message.toString() == "Sorry! Phone number is not associated with this email address.") {
                                var action: NavDirections =
                                    LoginDirections.actionLoginToNumberVerify()
                                findNavController().navigate(action)
                            }
                        }
                    }

                } else {
                    binding.emailError.visibility = View.VISIBLE
                    binding.emailError.text = getString(R.string.emailError2)
                }
            }
        }
        return binding.root
    }

    private fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


}