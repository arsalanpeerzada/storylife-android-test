package com.inksy.UI.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.inksy.R
import com.inksy.UI.Fragments.SelectedAudienceDirections
import com.inksy.databinding.ActivityPrivacyBinding

class Privacy : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var binding: ActivityPrivacyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.findFragmentById(binding.fragmentContainerView.id)?.let {
            navController = NavHostFragment.findNavController(it)

        }
    }
    override fun onBackPressed() {
        if (Navigation.findNavController(this, R.id.fragmentContainerView)
                .currentDestination?.id == R.id.selectedAudience
        ) {
            // handle back button the way you want here

            val action = SelectedAudienceDirections.actionSelectedAudienceToSelectAudience()
            navController.navigate(action)
            return;
        } else {
            this.finish()
        }
    }
}