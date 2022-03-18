package com.inksy.UI.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.example.DoodlePack
import com.example.example.Pack
import com.inksy.Remote.Status
import com.inksy.UI.Activities.ProfileActivity
import com.inksy.UI.Activities.ViewAll
import com.inksy.UI.Adapter.ArtworkAdapter
import com.inksy.UI.Constants
import com.inksy.UI.ViewModel.DoodleView
import com.inksy.Utils.TinyDB
import com.inksy.databinding.FragmentArtworkBinding

class Artwork : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var binding: FragmentArtworkBinding
    lateinit var doodleView: DoodleView
    var token: String = ""
    lateinit var tinyDB: TinyDB
    var feature: ArrayList<DoodlePack> = ArrayList()
    var pack: ArrayList<Pack> = ArrayList()
    lateinit var featureAdapter: ArtworkAdapter
    lateinit var packAdapter: ArtworkAdapter
    lateinit var refreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        doodleView = ViewModelProvider(requireActivity())[DoodleView::class.java]
        doodleView.init()

        tinyDB = TinyDB(requireContext())
        token = tinyDB.getString("token").toString()


        binding = FragmentArtworkBinding.inflate(layoutInflater)



        binding.seeall2.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), ViewAll::class.java).putExtra(
                    Constants.activity,
                    Constants.doodleViewAll
                )
            )
        }

        binding.seeall1.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), ViewAll::class.java).putExtra(
                    Constants.activity,
                    Constants.doodleViewAll
                )
            )

        }

        binding.profile.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }

        binding.search.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                openactivity()
                return@OnEditorActionListener true
            }
            false
        })
        refreshLayout = binding.swipe

        refreshLayout.setOnRefreshListener(this)
        refreshLayout.post(Runnable {
            refreshLayout.setRefreshing(true)

            // Fetching data from server
            getData(token)
        })


        return binding.root
    }

    private fun openactivity() {

        requireContext().startActivity(
            Intent(requireContext(), ViewAll::class.java).putExtra(
                "activity",
                Constants.doodleSearch
            )
        )

    }


    private fun performSearch() {
        binding.search.clearFocus()
        binding.search.text.clear()
        val input: InputMethodManager? =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input?.hideSoftInputFromWindow(binding.search.getWindowToken(), 0)
        //...perform search
    }


    fun getData(token: String) {

        doodleView.getData(token)?.observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    feature = it?.data?.data?.featuredPack!!
                    pack = it.data.data?.pack!!

                    featureAdapter = ArtworkAdapter(requireContext(), feature, "Feature")
                    binding.rvFeatured.adapter = featureAdapter

                    packAdapter = ArtworkAdapter(requireContext(), pack, "Pack")
                    binding.rvBestsellers.adapter = packAdapter
                    refreshLayout.isRefreshing = false;

                }
                Status.LOADING -> {}
                Status.ERROR -> {
                    refreshLayout.isRefreshing = false;
                }
            }
        }
    }


    override fun onRefresh() {
        getData(token)
    }


}