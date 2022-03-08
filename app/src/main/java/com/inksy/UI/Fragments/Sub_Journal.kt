package com.inksy.UI.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.inksy.Interfaces.OnChangeStateClickListener
import com.inksy.Interfaces.iOnClickListerner
import com.inksy.Model.Categories
import com.inksy.Model.Journals
import com.inksy.UI.Activities.CreateActivity
import com.inksy.UI.Activities.ViewAll
import com.inksy.UI.Adapter.BookAdapter
import com.inksy.UI.Constants
import com.inksy.UI.Dialogs.Comment_BottomSheet
import com.inksy.UI.ViewModel.DashboardView
import com.inksy.UI.ViewModel.JournalView
import com.inksy.Utils.TinyDB
import com.inksy.databinding.FragmentSubJournalBinding


class Sub_Journal(var list: ArrayList<Journals>, var _otherJournals: ArrayList<Journals>) :
    Fragment(), OnChangeStateClickListener {

    lateinit var binding: FragmentSubJournalBinding
    lateinit var dashboardView: DashboardView
    lateinit var categories: ArrayList<Categories>
    lateinit var otherJournals: ArrayList<Journals>
    lateinit var myjournals: ArrayList<Journals>
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

        if (list.isNotEmpty() || _otherJournals.isNotEmpty()) {
            myjournals = list

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
            otherJournals = _otherJournals as ArrayList<Journals>
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

//
//        binding.rvHealth.adapter = BookAdapter(requireContext(), list,Constants.person, object : iOnClickListerner {
//            override fun onclick(position: Int) {
//                Comment_BottomSheet().show(childFragmentManager, " ");
//            }
//        })
//        binding.rvHealth2.adapter = BookAdapter(requireContext(), list,Constants.person, object : iOnClickListerner {
//            override fun onclick(position: Int) {
//               var comment = Comment_BottomSheet().let {
//                    it.show(childFragmentManager, "")
//
//                }
//            }
//        })

        binding.createJournal.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), CreateActivity::class.java))
        }

        binding.seeall.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), ViewAll::class.java).putExtra(
                    Constants.activity,
                    Constants.sub_journalViewAll
                )
            )
        }
        binding.seeall1.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), ViewAll::class.java).putExtra(
                    Constants.activity,
                    Constants.sub_journalViewAll
                )
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

        return binding.root
    }

    override fun onStateChange(position: Int, like: Boolean) {
        super.onStateChange(position, like)

        if (like) {
            likeJournal(list[position].id, like)
        } else {

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
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


}