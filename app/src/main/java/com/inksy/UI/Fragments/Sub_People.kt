package com.inksy.UI.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.inksy.Interfaces.OnChangeStateClickListener
import com.inksy.Model.People
import com.inksy.Model.PeopleListModel
import com.inksy.Remote.Status
import com.inksy.UI.Adapter.PeopleDashboardAdapter
import com.inksy.UI.ViewModel.PeopleView
import com.inksy.Utils.TinyDB
import com.inksy.databinding.FragmentSubPeopleBinding

class Sub_People(var people: People) : Fragment(), OnChangeStateClickListener {

    lateinit var binding: FragmentSubPeopleBinding
    lateinit var peopleView: PeopleView
    lateinit var tinyDB: TinyDB
    lateinit var list: ArrayList<PeopleListModel>
    var token = ""
    lateinit var adapter: PeopleDashboardAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSubPeopleBinding.inflate(layoutInflater)
        peopleView = ViewModelProvider(requireActivity())[PeopleView::class.java]
        peopleView.init()

        tinyDB = TinyDB(requireContext())
        token = tinyDB.getString("token").toString()
        list = ArrayList()

        list = people.followers

        if (list.size > 0) {
            adapter = PeopleDashboardAdapter(requireContext(), list, false, this)
            binding.rvChat.adapter = adapter
        } else {
            binding.rvChat.visibility = View.GONE
            binding.layoutemptyPeople.visibility = View.VISIBLE


        }

        return binding.root
    }

    override fun onStateChange(position: Int, like: Boolean) {
        unfollowUser(list[position].id!!, position)
    }

    private fun unfollowUser(id: Int, position: Int) {

        var mytoken = "Bearer $token"
        peopleView.userUnfollow(id, mytoken)?.observe(requireActivity()) { it ->
            when (it?.status) {
                Status.SUCCESS -> {
                    Toast.makeText(
                        requireActivity(),
                        it.data?.message.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    adapter.notifyItemRemoved(position)
                    list.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
                Status.LOADING -> {}
                Status.ERROR -> {
                    Toast.makeText(requireActivity(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


}