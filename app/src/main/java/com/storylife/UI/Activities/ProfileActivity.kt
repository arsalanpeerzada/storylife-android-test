package com.storylife.UI.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.storylife.UI.Fragments.NumberVerify
import com.storylife.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    lateinit var navController: NavController
    lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.findFragmentById(binding.fragmentContainerView.id)?.let {
            navController = NavHostFragment.findNavController(it)

            it.childFragmentManager.primaryNavigationFragment?.let {
                if (it is NumberVerify) {
//                    finish()
                } else {
//                    super.onBackPressed()
                }
            }
        }
    }

    override fun onBackPressed() {

    }


}