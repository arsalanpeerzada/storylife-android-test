package com.storylife.UI.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.storylife.UI.Fragments.DashboardPending
import com.storylife.UI.Fragments.Dashboard_Analytics
import com.storylife.UI.Fragments.Dashboard_Approved
import com.storylife.databinding.ActivityArtistDashboardBinding


class ArtistDashboard : AppCompatActivity() {
    private lateinit var adapterViewPager: MyPagerAdapter
    lateinit var binding: ActivityArtistDashboardBinding
    lateinit var vpPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vpPager = binding.vpPager
        adapterViewPager = MyPagerAdapter(supportFragmentManager)
        vpPager.adapter = adapterViewPager


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
                0 -> Dashboard_Analytics()
                1 -> Dashboard_Approved()
                2 -> DashboardPending()
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