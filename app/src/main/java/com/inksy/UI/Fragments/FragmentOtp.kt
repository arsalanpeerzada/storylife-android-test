package com.inksy.UI.Fragments

import android.content.Context
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
import androidx.navigation.fragment.findNavController
import com.inksy.Interfaces.OnKeyboardVisibilityListener
import com.inksy.R
import com.inksy.databinding.FragmentOtpBinding


class FragmentOtp : Fragment(), OnKeyboardVisibilityListener {

    lateinit var binding: FragmentOtpBinding
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

        val mobileNumber = arguments?.getString("number")
        binding.fragmentotptitle.text = getString(R.string.digit_code) + mobileNumber

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


        binding.imageView2.setOnClickListener() {

            val number = arguments?.getString("number").toString()
            val code = arguments?.getString("code").toString()
            val bundle = Bundle()
            bundle.putString("number",number )
            bundle.putString("code", code)

            val action = FragmentOtpDirections.actionFragmentOtpToNumberVerify()

            findNavController().navigate(R.id.action_fragmentOtp_to_numberVerify,bundle)
        }


        binding.otpView.requestFocus()

        binding.otpView.setOtpCompletionListener {

            val number = arguments?.getString("number").toString()
            val code = arguments?.getString("code").toString()

            val bundle = Bundle()
            bundle.putString("number", number)
            bundle.putString("code", code)

            val action = FragmentOtpDirections.actionFragmentOtpToLogin(
            )

            findNavController().navigate(R.id.action_fragmentOtp_to_login,bundle)
        }

        return binding.root
    }


    private fun setKeyboardVisibilityListener(onKeyboardVisibilityListener: OnKeyboardVisibilityListener) {
        val parentView: View = (requireActivity().findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
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