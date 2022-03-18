package com.inksy.UI.Activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.inksy.Interfaces.iOnClickListerner
import com.inksy.Model.PeopleListModel
import com.inksy.R
import com.inksy.Remote.Status
import com.inksy.UI.Adapter.UsersListAdapter
import com.inksy.UI.ViewModel.PeopleView
import com.inksy.Utils.TinyDB
import com.inksy.databinding.ActivityListBinding

class List : AppCompatActivity(), iOnClickListerner, SwipeRefreshLayout.OnRefreshListener {

    var followRequest = false
    lateinit var peopleView: PeopleView
    lateinit var binding: ActivityListBinding
    var token = " "
    var list: ArrayList<PeopleListModel> = ArrayList()
    lateinit var adapter: UsersListAdapter
    lateinit var refreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        followRequest = intent.getBooleanExtra("followRequests", false)

        if (followRequest){
         binding.title.text = "Follow Requests"

        }else {
            binding.title.text = "Blocked Users"
        }

        peopleView = ViewModelProvider(this)[PeopleView::class.java]
        peopleView.init()

        token = TinyDB(this).getString("token").toString()

        refreshLayout = binding.swipe

        refreshLayout.setOnRefreshListener(this)
        refreshLayout.post(Runnable {
            refreshLayout.setRefreshing(true)

            // Fetching data from server
            if (followRequest) {
                getDataFollow(token)
            } else {
                getDataBlock(token)
            }

        })
    }

    private fun getDataBlock(_token: String) {
        peopleView.blockList(_token)?.observe(this) {
            when (it.status) {
                Status.LOADING -> {}
                Status.ERROR -> {
                    refreshLayout.isRefreshing = false;
                }
                Status.SUCCESS -> {
                    refreshLayout.isRefreshing = false;
                    list = it?.data?.data as ArrayList<PeopleListModel>
                    adapter = UsersListAdapter(this, list, this, followRequest)
                    binding.rvItemList.adapter = adapter

                    binding.layoutblockeduser.visibility = View.GONE
                    binding.rvItemList.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getDataFollow(_token: String) {
        peopleView.followRequests(_token)?.observe(this) {
            when (it.status) {
                Status.LOADING -> {}
                Status.ERROR -> {
                    refreshLayout.isRefreshing = false;
                }
                Status.SUCCESS -> {
                    refreshLayout.isRefreshing = false;
                    list = it?.data?.data as ArrayList<PeopleListModel>
                    adapter = UsersListAdapter(this, list, this,followRequest)
                    binding.rvItemList.adapter = adapter

                    binding.layoutblockeduser.visibility = View.GONE
                    binding.rvItemList.visibility = View.VISIBLE

                }
            }
        }
    }

    override fun onclick(position: Int) {
        super.onclick(position)
        unblockUser(list[position].id!!, token, position)
    }

    fun unblockUser(id: Int, token: String, position: Int) {
        peopleView.userUnblock(id, token)?.observe(this) { it ->
            when (it.status) {
                Status.ERROR -> {
                }
                Status.SUCCESS -> {
                    Toast.makeText(this, it.data?.message, Toast.LENGTH_SHORT).show()
                    refresh()
                }
                Status.LOADING -> {
                }
            }
        }
    }

    override fun onRefresh() {
        refresh()
    }

    fun refresh() {
        if (followRequest) {
            getDataFollow(token)
        } else {
            getDataBlock(token)
        }

    }
}