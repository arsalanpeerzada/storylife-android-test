package com.storylife.UI.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hbb20.CountryCodePicker
import com.storylife.R
import com.storylife.databinding.FragmentNumberVerifyBinding


class NumberVerify : Fragment() {

    lateinit var binding: FragmentNumberVerifyBinding
    lateinit var ccp: CountryCodePicker

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNumberVerifyBinding.inflate(layoutInflater)
        ccp = binding.ccp
        binding.nextbutton.setOnClickListener {
//
//            if (!binding.number.text.isNullOrEmpty()) {
//                if (ccp.isValidFullNumber) {

                    val action = NumberVerifyDirections.actionNumberVerifyToFragmentOtp(
                        binding.number.text.toString()
                    )

                    findNavController().navigate(action)

//                } else {
//                    binding.number.text.clear()
//                    binding.number.textSize = 10f
//                    binding.number.setHintTextColor(resources.getColor(R.color.errorRed))
//                    binding.number.hint = getString(R.string.NumberValidError)
//
//                }
//            } else {
//                binding.number.textSize = 10f
//                binding.number.setHintTextColor(resources.getColor(R.color.errorRed))
//                binding.number.hint = getString(R.string.EmptryNumberError)
//
//            }
        }

        binding.number.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.number.textSize = 16f
                binding.number.hint = null
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        return binding.root
    }

//    companion object {
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            NumberVerify().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}