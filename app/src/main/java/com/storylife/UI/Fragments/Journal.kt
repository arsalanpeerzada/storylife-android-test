package com.storylife.UI.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.storylife.R
import com.storylife.UI.Activities.ProfileActivity
import com.storylife.databinding.FragmentJournalBinding

class Journal : Fragment() {
    lateinit var vpPager: ViewPager
    lateinit var binding: FragmentJournalBinding
    private lateinit var adapterViewPager: MyPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentJournalBinding.inflate(layoutInflater)


        var v: View = binding.include3

        var tab1: TextView = v.findViewById(R.id.tab1)
        var tab2: TextView = v.findViewById(R.id.tab2)

        tab1.text = getString(R.string.journal)
        tab2.text = getString(R.string.people)


        vpPager = binding.vpPager
        adapterViewPager = MyPagerAdapter(childFragmentManager)
        vpPager.adapter = adapterViewPager


        vpPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    tab1.setTextColor(resources.getColor(R.color.appBlue))
                    tab2.setTextColor(resources.getColor(R.color.white))
                    tab1.setBackgroundResource(R.drawable.round_border_edittext_blue)
                    tab2.setBackgroundResource(R.drawable.round_border_edittext_gradient)

                } else if (position == 1) {
                    tab2.setTextColor(resources.getColor(R.color.appBlue))
                    tab1.setTextColor(resources.getColor(R.color.white))
                    tab2.setBackgroundResource(R.drawable.round_border_edittext_blue)
                    tab1.setBackgroundResource(R.drawable.round_border_edittext_gradient)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        tab1.setOnClickListener {

            tab1.setTextColor(resources.getColor(R.color.appBlue))
            tab2.setTextColor(resources.getColor(R.color.white))
            tab1.setBackgroundResource(R.drawable.round_border_edittext_blue)
            tab2.setBackgroundResource(R.drawable.round_border_edittext_gradient)
            tabOnlick()
        }
        tab2.setOnClickListener {
            tab2.setTextColor(resources.getColor(R.color.appBlue))
            tab1.setTextColor(resources.getColor(R.color.white))
            tab2.setBackgroundResource(R.drawable.round_border_edittext_blue)
            tab1.setBackgroundResource(R.drawable.round_border_edittext_gradient)

            tabOnlick()
        }

        binding.profile.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }
        return binding.root
    }

    fun tabOnlick() {
        val viewPagerCurrentItem: Int = vpPager.currentItem
        if (viewPagerCurrentItem == 0) {
            vpPager.currentItem = 1
        } else if (viewPagerCurrentItem == 1) {
            vpPager.currentItem = 0
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
                0 -> Sub_Journal()
                1 -> Sub_People()

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