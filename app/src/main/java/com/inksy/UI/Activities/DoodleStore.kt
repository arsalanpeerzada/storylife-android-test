package com.inksy.UI.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.inksy.R
import com.inksy.UI.Constants
import com.inksy.UI.Fragments.DoodleSubStore
import com.inksy.databinding.ActivityDoodleStoreBinding
import com.inksy.databinding.Tablayout1Binding

class DoodleStore : AppCompatActivity() {
    private lateinit var adapterViewPager: MyPagerAdapter
    lateinit var binding: ActivityDoodleStoreBinding
    lateinit var vpPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoodleStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)



        vpPager = binding.vpPager
        adapterViewPager = MyPagerAdapter(supportFragmentManager)
        vpPager.adapter = adapterViewPager

        var v: Tablayout1Binding = binding.include5

//        var tab1: TextView = v.findViewById(R.id.tab1)
//        var tab2: TextView = v.findViewById(R.id.tab2)

        v.tab1.text = getString(R.string.store)
        v.tab2.text = getString(R.string.purchase)

        vpPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    v.tab1.setTextColor(resources.getColor(R.color.appBlue))
                    v.tab2.setTextColor(resources.getColor(R.color.white))
                    v.tab1.setBackgroundResource(R.drawable.round_border_edittext_blue)
                    v.tab2.setBackgroundResource(R.drawable.round_border_edittext_gradient)

                } else if (position == 1) {
                    v.tab2.setTextColor(resources.getColor(R.color.appBlue))
                    v.tab1.setTextColor(resources.getColor(R.color.white))
                    v.tab2.setBackgroundResource(R.drawable.round_border_edittext_blue)
                    v.tab1.setBackgroundResource(R.drawable.round_border_edittext_gradient)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        v.tab1.setOnClickListener {

            v.tab1.setTextColor(resources.getColor(R.color.appBlue))
            v.tab2.setTextColor(resources.getColor(R.color.white))
            v.tab1.setBackgroundResource(R.drawable.round_border_edittext_blue)
            v.tab2.setBackgroundResource(R.drawable.round_border_edittext_gradient)
            val viewPagerCurrentItem: Int = vpPager.currentItem

            vpPager.currentItem = 0

        }
        v.tab2.setOnClickListener {
            v.tab2.setTextColor(resources.getColor(R.color.appBlue))
            v.tab1.setTextColor(resources.getColor(R.color.white))
            v.tab2.setBackgroundResource(R.drawable.round_border_edittext_blue)
            v.tab1.setBackgroundResource(R.drawable.round_border_edittext_gradient)
            val viewPagerCurrentItem: Int = vpPager.currentItem

            vpPager.currentItem = 1

        }

        binding.back.setOnClickListener() {
            onBackPressed()
            this.finish()
        }

        binding.search.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                openactivity()
                return@OnEditorActionListener true
            }
            false
        })

    }

    private fun openactivity() {

        startActivity(
            Intent(this, ViewAll::class.java).putExtra(
                "activity",
                Constants.doodleSearch
            )
        )
    }

    private fun performSearch() {
        binding.search.clearFocus()
        binding.search.text.clear()
        val input: InputMethodManager? = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input?.hideSoftInputFromWindow(binding.search.getWindowToken(), 0)
        //...perform search
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