package com.storylife.UI.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.storylife.UI.Fragments.DoodleSubStore
import com.storylife.databinding.ActivityDoodleStoreBinding

class DoodleStore : AppCompatActivity() {
    private lateinit var adapterViewPager: MyPagerAdapter
    lateinit var binding: ActivityDoodleStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoodleStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val vpPager = binding.vpPager
        adapterViewPager = MyPagerAdapter(supportFragmentManager)
        vpPager.adapter = adapterViewPager

        binding.back.setOnClickListener() {
            onBackPressed()
            this.finish()
        }

    }

    class MyPagerAdapter(fragmentManager: FragmentManager?) :
        FragmentPagerAdapter(fragmentManager!!) {
        // Returns total number of pages
        override fun getCount(): Int {
            return NUM_ITEMS
        }

        // Returns the fragment to display for that page
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> DoodleSubStore()
                1 -> DoodleSubStore()

                else -> null!!
            }
        }

        // Returns the page title for the top indicator
        override fun getPageTitle(position: Int): CharSequence? {
            return "$position"
        }

        companion object {
            private const val NUM_ITEMS = 2
        }
    }
}