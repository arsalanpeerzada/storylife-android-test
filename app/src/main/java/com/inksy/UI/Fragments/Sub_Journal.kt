package com.inksy.UI.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.inksy.Interfaces.OnChangeStateClickListener
import com.inksy.Interfaces.iOnClickListerner
import com.inksy.Model.Journals
import com.inksy.Remote.Status
import com.inksy.UI.Activities.CreateActivity
import com.inksy.UI.Activities.StartingActivity
import com.inksy.UI.Activities.ViewAll
import com.inksy.UI.Adapter.BookAdapter
import com.inksy.UI.Constants
import com.inksy.UI.Dialogs.Comment_BottomSheet
import com.inksy.UI.ViewModel.DashboardView
import com.inksy.UI.ViewModel.JournalView
import com.inksy.Utils.TinyDB
import com.inksy.databinding.FragmentSubJournalBinding
import java.io.Serializable


class Sub_Journal :
    Fragment(), OnChangeStateClickListener, SwipeRefreshLayout.OnRefreshListener {

    lateinit var binding: FragmentSubJournalBinding
    lateinit var dashboardView: DashboardView
    var otherJournals: ArrayList<Journals> = ArrayList()
    var myjournals: ArrayList<Journals> = ArrayList()
    lateinit var refreshLayout: SwipeRefreshLayout
    var token: String = ""
    lateinit var tinyDB: TinyDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSubJournalBinding.inflate(layoutInflater)
        tinyDB = TinyDB(requireContext())
        token = tinyDB.getString("token")!!
        refreshLayout = binding.refreshListener
        dashboardView = ViewModelProvider(requireActivity())[DashboardView::class.java]
        binding.createJournal.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), CreateActivity::class.java))
        }

        binding.seeall.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), ViewAll::class.java).putExtra(
                    Constants.activity,
                    Constants.sub_journalViewAll
                ).putExtra("Data", true)
                    .putExtra("List", myjournals as Serializable)
            )
        }
        binding.seeall1.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), ViewAll::class.java).putExtra(
                    Constants.activity,
                    Constants.sub_journalViewAll
                ).putExtra("Data", true)
                    .putExtra("List", otherJournals as Serializable)
            )
        }
        binding.seeall2.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), ViewAll::class.java).putExtra(
                    Constants.activity,
                    Constants.sub_journalViewAll
                )
            )
        }


        refreshLayout.setOnRefreshListener(this)
        refreshLayout.post(Runnable {
            refreshLayout.setRefreshing(true)

            // Fetching data from server
            getData()
        })


        return binding.root
    }

    override fun onStateChange(position: Int, like: Boolean) {
        super.onStateChange(position, like)

        if (like) {
            likeJournal(myjournals[position].id, like)
        } else {
            likeJournal(myjournals[position].id, like)
        }
    }


    private fun likeJournal(id: Int?, like: Boolean) {
        val journalView: JournalView =
            ViewModelProviders.of(requireActivity())[JournalView::class.java]
        journalView.init()
        journalView.journalLike(
            id.toString(),
            token
        )?.observe(requireActivity()) {

            if (it?.data?.status == 1) {
                Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), it?.data?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getData() {


        val mytoken = "Bearer $token"

        dashboardView.getData(mytoken)?.observe(requireActivity()) { it ->

            when (it.status) {
                Status.SUCCESS -> {

                    // people = it.data?.data?.people
                    myjournals = it.data?.data?.journals!!
                    otherJournals = it.data?.data?.followedJournals!!

                    var categoriesList = it.data?.data!!.categories
                    var list = ArrayList<String>()
                    for (i in 0 until categoriesList!!.size) {
                        var json = categoriesList.get(i)
                        var gson = Gson()
                        list.add(gson.toJson(json))

                    }
                    tinyDB.putListString("categoriesList", list)
                    populate()

                    refreshLayout.isRefreshing = false;
                }

                Status.ERROR -> {
                    refreshLayout.isRefreshing = false;

                    tinyDB.clear()

                    requireContext().startActivity(
                        Intent(
                            requireContext(),
                            StartingActivity::class.java
                        )
                    )
                    Toast.makeText(requireContext(), "Token Expired", Toast.LENGTH_SHORT).show()
                    refreshLayout.isRefreshing = false;
                }
                Status.LOADING -> {}

            }
        }
    }


    fun populate() {
        if (myjournals.isNotEmpty() || otherJournals.isNotEmpty()) {
            myjournals = myjournals

            binding.myJournal.adapter = BookAdapter(
                requireContext(),
                myjournals,
                " ",
                object : iOnClickListerner {
                    override fun onclick(position: Int) {
                        Comment_BottomSheet().show(childFragmentManager, " ");
                    }
                },
                this
            )
            otherJournals = otherJournals as ArrayList<Journals>
            binding.rvHealth.adapter = BookAdapter(
                requireContext(),
                otherJournals,
                " ",
                object : iOnClickListerner {
                    override fun onclick(position: Int) {
                        Comment_BottomSheet().show(childFragmentManager, " ");
                    }
                },
                this
            )

        } else {
            binding.layout.visibility = View.GONE
            binding.layoutemptyChat.visibility = View.VISIBLE
        }
    }

    override fun onRefresh() {
        refresh()
    }

    fun refresh() {
        getData()

    }

    override fun onResume() {
        refresh()
        super.onResume()
    }


}