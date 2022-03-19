package com.inksy.UI.Fragments

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.inksy.Interfaces.OnKeyboardVisibilityListener
import com.inksy.R
import com.inksy.Remote.Status
import com.inksy.UI.Activities.MainActivity
import com.inksy.UI.ViewModel.LoginView
import com.inksy.UI.ViewModel.PeopleView
import com.inksy.Utils.TinyDB
import com.inksy.databinding.FragmentOtpBinding


class FragmentOtp : Fragment(), OnKeyboardVisibilityListener {
    lateinit var peopleView: PeopleView
    lateinit var tinyDB: TinyDB
    var token = ""
    lateinit var binding: FragmentOtpBinding
    var forgetPasswordData: Boolean = false
    var registerData: Boolean = false
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
        binding = FragmentOtpBinding.inflate(layoutInflater)

        peopleView = ViewModelProvider(this)[PeopleView::class.java]
        peopleView.init()

        tinyDB = TinyDB(requireContext())
        token = tinyDB.getString("token").toString()

        val mobileNumber = arguments?.getString("number")
        var register = arguments?.getString("register")

        forgetPasswordData = arguments?.getBoolean("forgetPassword", false)!!
        registerData = arguments?.getBoolean("RegisterData", false)!!

        if (!mobileNumber.isNullOrEmpty()) {
            binding.fragmentotptitle.text = getString(R.string.digit_code) + mobileNumber
        } else if (!register.isNullOrEmpty()) {
            binding.fragmentotptitle.text = "Enter 4 digit code to verify the email address"
            binding.otpView.itemCount = 4
        } else {
            binding.fragmentotptitle.text = "Enter 4 digit code to change your password"
            binding.otpView.itemCount = 4

        }

        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timer.text = getString(R.string.resend_code) + millisUntilFinished / 1000
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {
                binding.timer.text = getString(R.string.resend_code_)
            }
        }.start()

        binding.layMain.viewTreeObserver.addOnGlobalLayoutListener {
            val rec = Rect()
            binding.layMain.getWindowVisibleDisplayFrame(rec)
            val screenHeight = binding.layMain.rootView.height
            val keypadHeight = screenHeight - rec.bottom
            if (keypadHeight > screenHeight * 0.15) {
            } else {
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        val imm: InputMethodManager =
                            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.showSoftInput(binding.otpView, InputMethodManager.SHOW_IMPLICIT)
                    }

                }, 100)
            }
        }


        binding.back.setOnClickListener() {

            if (!mobileNumber.isNullOrEmpty()) {
                val number = arguments?.getString("number").toString()
                val code = arguments?.getString("code").toString()
                val bundle = Bundle()
                bundle.putString("number", number)
                bundle.putString("code", code)

                findNavController().navigate(R.id.action_fragmentOtp_to_numberVerify, bundle)
            } else if (!register.isNullOrEmpty()) {
                findNavController().navigate(R.id.action_fragmentOtp_to_login)
            } else {
                findNavController().navigate(R.id.action_fragmentOtp_to_login)
            }

        }


        binding.otpView.requestFocus()

        binding.otpView.setOtpCompletionListener()
        {
            if (forgetPasswordData) {


                binding.loader.visibility = View.VISIBLE
                var email = arguments?.getString("email")
                verifyEmail(email, token, "0000")


            } else if (registerData) {
                binding.loader.visibility = View.VISIBLE
                var email = arguments?.getString("email")
                var password = arguments?.getString("password")
                var phone = arguments?.getString("phone")
                var phoneCode = arguments?.getString("phoneCode")


                login(email!!, password!!, phone!!, phoneCode!!, "0000")

            } else {

                val number = arguments?.getString("number").toString()
                val code = arguments?.getString("code").toString()

                val bundle = Bundle()
                bundle.putString("number", number)
                bundle.putString("code", code)

                findNavController().navigate(R.id.action_fragmentOtp_to_login, bundle)

            }
        }

        return binding.root
    }

    private fun login(
        email: String,
        password: String,
        phone: String,
        phonecode: String,
        code: String
    ) {
        loginView = ViewModelProvider(requireActivity())[LoginView::class.java]
        loginView.init()
        loginView.loginRegister(
            email,
            password,
            phone,
            phonecode,
            code
        )?.observe(requireActivity()) {
            binding.loader.visibility = View.GONE
            if (it?.status == 1) {

                if (it?.data?.is_email_verification == 0) {
                    var email = it.data?.email

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
                    tinydb.putString(
                        "isprivate",
                        it.data?.isPrivateProfile!!.toString()
                    )
                    tinydb.putInt("isprofilecompleted", it.data?.isProfileCompleted!!)
                    tinydb.putInt("isArtist", it?.data?.is_artist!!)

                    if (it.data?.isProfileCompleted == 0) {

                        findNavController().navigate(R.id.action_fragmentOtp_to_bio)
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

                    findNavController().navigate(R.id.action_fragmentOtp_to_bio)
                }
            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun verifyEmail(email: String?, token: String, mycode: String) {
        peopleView.verifyCode(mycode, email!!, token)
            ?.observe(requireActivity()) {
                binding.loader.visibility = View.GONE
                when (it?.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(
                            requireContext(),
                            it?.data?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        var bundle = Bundle()
                        bundle.putBoolean("forgetPassword", true)
                        bundle.putString("email", email)
                        bundle.putString("code", mycode)
                        findNavController().navigate(
                            R.id.action_fragmentOtp_to_forgetPassword2,
                            bundle
                        )
                    }
                    Status.ERROR -> {
//                        tinyDB.clear()
//
//                        requireContext().startActivity(
//                            Intent(
//                                requireContext(),
//                                StartingActivity::class.java
//                            )
//                        )
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


    private fun setKeyboardVisibilityListener(onKeyboardVisibilityListener: OnKeyboardVisibilityListener) {
        val parentView: View =
            (requireActivity().findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
        parentView.viewTreeObserver
            .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                private var alreadyOpen = false
                private val defaultKeyboardHeightDP = 100
                private val EstimatedKeyboardDP =
                    defaultKeyboardHeightDP + if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) 48 else 0
                private val rect: Rect = Rect()
                override fun onGlobalLayout() {
                    val estimatedKeyboardHeight = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        EstimatedKeyboardDP.toFloat(),
                        parentView.getResources().getDisplayMetrics()
                    )
                        .toInt()
                    parentView.getWindowVisibleDisplayFrame(rect)
                    val heightDiff: Int =
                        parentView.getRootView().getHeight() - (rect.bottom - rect.top)
                    val isShown = heightDiff >= estimatedKeyboardHeight
                    if (isShown == alreadyOpen) {
                        Log.d("Keyboard state", "Ignoring global layout change...")
                        return
                    }
                    alreadyOpen = isShown
                    onKeyboardVisibilityListener.onVisibilityChanged(isShown)
                }
            })
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden === Configuration.HARDKEYBOARDHIDDEN_NO) {
            Toast.makeText(requireContext(), "keyboard visible", Toast.LENGTH_SHORT).show()
        } else if (newConfig.hardKeyboardHidden === Configuration.HARDKEYBOARDHIDDEN_YES) {
            Toast.makeText(requireContext(), "keyboard hidden", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onVisibilityChanged(visible: Boolean) {
        if (!visible) {
            val imm: InputMethodManager =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.otpView, InputMethodManager.SHOW_IMPLICIT)
        }

    }

    override fun onResume() {
        super.onResume()
        binding.otpView.requestFocus()


        val imm: InputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.otpView, InputMethodManager.SHOW_IMPLICIT)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)


    }


}