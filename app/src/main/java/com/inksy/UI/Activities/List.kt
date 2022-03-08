package com.inksy.UI.Activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.inksy.Interfaces.iOnClickListerner
import com.inksy.Model.PeopleListModel
import com.inksy.Remote.Status
import com.inksy.UI.Adapter.BlockedUsersAdapter
import com.inksy.UI.ViewModel.PeopleView
import com.inksy.Utils.TinyDB
import com.inksy.databinding.ActivityListBinding

class List : AppCompatActivity(), iOnClickListerner {


    lateinit var peopleView: PeopleView
    lateinit var binding: ActivityListBinding
    var token = " "
    var list: ArrayList<PeopleListModel> = ArrayList()
    lateinit var adapter: BlockedUsersAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        peopleView = ViewModelProvider(this)[PeopleView::class.java]
        peopleView.init()

        token = TinyDB(this).getString("token").toString()

        getData(token)
    }

    private fun getData(_token: String) {
        peopleView.blockList(_token)?.observe(this) {
            when (it.status) {
                Status.LOADING -> {}
                Status.ERROR -> {}
                Status.SUCCESS -> {

                    list = it?.data?.data as ArrayList<PeopleListModel>
                    adapter = BlockedUsersAdapter(this, list, this)
                    binding.rvItemList.adapter = adapter

                    binding.layoutblockeduser.visibility = View.VISIBLE
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
                    adapter.notifyItemRemoved(position)
                }
                Status.LOADING -> {

                }
            }
        }
    }
}