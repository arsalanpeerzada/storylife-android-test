package com.inksy.UI.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.inksy.Model.Categories
import com.inksy.Model.Journals
import com.inksy.Model.People
import com.inksy.R
import com.inksy.Remote.Status
import com.inksy.UI.Activities.ProfileActivity
import com.inksy.UI.Activities.ViewAll
import com.inksy.UI.Constants
import com.inksy.UI.ViewModel.DashboardView
import com.inksy.Utils.TinyDB
import com.inksy.databinding.FragmentJournalBinding
import com.inksy.databinding.Tablayout1Binding
import java.io.Serializable


class Journal : Fragment() {
    lateinit var vpPager: ViewPager
    lateinit var binding: FragmentJournalBinding
    private lateinit var adapterViewPager: MyPagerAdapter
    lateinit var v: Tablayout1Binding
    lateinit var dashboardView: DashboardView
    lateinit var people: People
    lateinit var tinyDB: TinyDB

    var token: String = " "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentJournalBinding.inflate(layoutInflater)
        vpPager = binding.vpPager
        dashboardView = ViewModelProvider(requireActivity())[DashboardView::class.java]
        dashboardView.init()
        tinyDB = TinyDB(requireContext())

        if (!tinyDB.getString("avatar").isNullOrEmpty()) {
            Glide.with(requireContext()).load(Constants.BASE_IMAGE + tinyDB.getString("avatar"))
                .placeholder(R.drawable.ic_empty_user)
                .into(binding.profile)
        }else {

        }

        Handler().postDelayed({
            binding.loader.visibility = View.GONE
        }, 3000)

        token = tinyDB.getString("token").toString()
        v = binding.include3

//        var tab1: TextView = v.tab1
//        var tab2: TextView = v.findViewById(R.id.tab2)

        v.tab1.text = getString(R.string.journal)
        v.tab2.text = getString(R.string.people)
        // getData()

        v.tab1.setOnClickListener {
            selectTab(0)

            vpPager.currentItem = 0

        }
        v.tab2.setOnClickListener {

            selectTab(1)
            vpPager.currentItem = 1

        }

        binding.profile.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }
        binding.search.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                var text = binding.search.text.toString()
                performSearch()
                openactivity(text)
                return@OnEditorActionListener true
            }
            false
        })

        selectTab(0)
        setupViewPager()


//        getData()
        return binding.root
    }


    private fun openactivity(text: String) {

        Log.d("searchText", text)
        if (vpPager.currentItem == 0) {

            searchUser(text, 0)

        } else if (vpPager.currentItem == 1) {
            searchUser(text, 1)

        }
    }

    private fun performSearch() {
        binding.search.clearFocus()
        binding.search.text.clear()
        val input: InputMethodManager? =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input?.hideSoftInputFromWindow(binding.search.windowToken, 0)
        //...perform search
    }

    class MyPagerAdapter(
        fragmentManager: FragmentManager?,
    ) : FragmentPagerAdapter(fragmentManager!!) {
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

    private fun selectTab(position: Int) {
        when (position) {
            0 -> {
                v.tab1.setTextColor(resources.getColor(R.color.appBlue))
                v.tab2.setTextColor(resources.getColor(R.color.white))
                v.tab1.setBackgroundResource(R.drawable.round_border_edittext_blue)
                v.tab2.setBackgroundResource(R.drawable.round_border_edittext_gradient)

            }
            1 -> {
                v.tab2.setTextColor(resources.getColor(R.color.appBlue))
                v.tab1.setTextColor(resources.getColor(R.color.white))
                v.tab2.setBackgroundResource(R.drawable.round_border_edittext_blue)
                v.tab1.setBackgroundResource(R.drawable.round_border_edittext_gradient)
            }
        }
    }

//    private fun getData() {
//
//
//        val mytoken = "Bearer $token"
//        val image = tinyDB.getString("avatar")
//        if (!image.isNullOrEmpty())
//            Glide.with(requireContext()).load(Constants.BASE_IMAGE + image).into(binding.profile)
//
//        dashboardView.getData(mytoken)?.observe(requireActivity()) { it ->
//
//            when (it.status) {
//                Status.SUCCESS -> {
//
//                    categories = it?.data?.data?.categories!!
//
//                    myjournals = it.data.data?.journals!!
//
//                    otherJournals = it.data.data?.followedJournals!!
//
//                    people = it.data.data?.people!!
//
//
//
//                }
//
//                Status.ERROR -> {
//
//                    tinyDB.clear()
//
//                    requireContext().startActivity(
//                        Intent(
//                            requireContext(),
//                            StartingActivity::class.java
//                        )
//                    )
//                    Toast.makeText(requireContext(), "Token Expired", Toast.LENGTH_SHORT).show()
//                    refreshLayout.isRefreshing = false;
//                }
//                Status.LOADING -> {}
//
//            }
//        }
//    }

    fun searchUser(text: String, i: Int) {

        val mytoken = "Bearer $token"

        if (i == 0){
            dashboardView.searchJournal(text, mytoken)?.observe(requireActivity()) { it ->

                when (it.status) {
                    Status.SUCCESS -> {

                        var data = it?.data?.data

                        requireContext().startActivity(
                            Intent(requireContext(), ViewAll::class.java)
                                .putExtra(Constants.activity, Constants.sub_journalSearch)
                                .putExtra("List", data as Serializable)
                                .putExtra("Data", true)
                        )
                    }

                    Status.ERROR -> {
                    }
                    Status.LOADING -> {}

                }
            }
        }else {
            dashboardView.searchUser(text, mytoken)?.observe(requireActivity()) { it ->

                when (it.status) {
                    Status.SUCCESS -> {

                        var data = it?.data?.data

                        requireContext().startActivity(
                            Intent(requireContext(), ViewAll::class.java)
                                .putExtra(Constants.activity, Constants.peopleSearch)
                                .putExtra("List", data as Serializable)
                                .putExtra("Data", true)
                        )


                    }

                    Status.ERROR -> {

                    }
                    Status.LOADING -> {}

                }
            }
        }


    }

    fun setupViewPager(

    ) {

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
                selectTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }


}