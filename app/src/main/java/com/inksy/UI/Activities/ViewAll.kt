package com.inksy.UI.Activities

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.inksy.Interfaces.OnChangeStateClickListener
import com.inksy.Interfaces.iOnClickListerner
import com.inksy.Model.Journals
import com.inksy.Model.PeopleListModel
import com.inksy.Model.UserModel
import com.inksy.R
import com.inksy.Remote.Status
import com.inksy.UI.Adapter.DoodleAdapter
import com.inksy.UI.Adapter.JournalAdapter
import com.inksy.UI.Adapter.PeopleAdapter
import com.inksy.UI.Constants
import com.inksy.UI.Dialogs.Comment_BottomSheet
import com.inksy.UI.ViewModel.PeopleView
import com.inksy.Utils.TinyDB
import com.inksy.databinding.ActivityViewAllBinding


class ViewAll : AppCompatActivity(), iOnClickListerner, OnChangeStateClickListener {


    lateinit var tinyDB: TinyDB
    lateinit var peopleView: PeopleView
    lateinit var binding: ActivityViewAllBinding
    var activity: String = ""
    var peoplelist = ArrayList<UserModel>()
    var journallist = ArrayList<Journals>()
    var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvAll.visibility = View.VISIBLE
        binding.layoutempty.visibility = View.GONE

        peopleView = ViewModelProvider(this)[PeopleView::class.java]
        peopleView.init()

        tinyDB = TinyDB(this)
        token = tinyDB.getString("token").toString()

        activity = intent.getStringExtra(Constants.activity).toString()
        var dataCheck = intent.getBooleanExtra("Data", false)
        if (activity.contains("Search")) {
            binding.text.text = getString(R.string.search_result)
            binding.search.visibility = View.GONE
        } else if (activity.contains("View")) {
            binding.text.text = getString(R.string.all)
            binding.search.visibility = View.VISIBLE
        }


        if (dataCheck) {

            if (activity.contains("Journal")) {
                journallist = intent.getSerializableExtra("List") as ArrayList<Journals>
                if (journallist.size == 0) {
                    binding.rvAll.visibility = View.GONE
                    binding.layoutempty.visibility = View.VISIBLE

                    Glide.with(this)
                        .load(ContextCompat.getDrawable(this, R.drawable.ic_empty_journal)).into(
                            binding.emptyuser
                        )
                    binding.emptytv.text = "No Journal Found"

                }
            } else if (activity.contains("People")) {
                peoplelist = intent.getSerializableExtra("List") as ArrayList<UserModel>
                if (peoplelist.size == 0) {
                    binding.rvAll.visibility = View.GONE
                    binding.layoutempty.visibility = View.VISIBLE
                }
            }else {

            }

        } else {
        }

        if (activity.contains(Constants.sub_journalViewAll)) {

            binding.rvAll.adapter =
                JournalAdapter(this@ViewAll, journallist, Constants.people, this)
        } else if (activity.contains(Constants.doodleViewAll)) {


            binding.rvAll.layoutManager = GridLayoutManager(this, 2)

            binding.rvAll.adapter = DoodleAdapter(this@ViewAll)
        } else if (activity.contains(Constants.peopleSearch)) {
            binding.search.visibility = View.GONE

            binding.rvAll.adapter =
                PeopleAdapter(this@ViewAll, peoplelist as ArrayList<UserModel>, true, this)
        } else if (activity.contains(Constants.peopleViewAll)) {

            binding.rvAll.adapter = PeopleAdapter(this@ViewAll, peoplelist, false, this)
        } else if (activity.contains(Constants.sub_journalSearch)) {
            binding.search.visibility = View.GONE

            binding.rvAll.adapter =
                JournalAdapter(this@ViewAll, journallist, Constants.people, this)
        } else if (activity.contains(Constants.doodleSearch)) {
            binding.search.visibility = View.GONE


            binding.rvAll.layoutManager = GridLayoutManager(this, 2)

            binding.rvAll.adapter = DoodleAdapter(this@ViewAll)
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.search.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                return@OnEditorActionListener true
            }
            false
        })


    }

    override fun onBackPressed() {
        super.onBackPressed()
        //  startActivity(Intent(this@ViewAll, MainActivity::class.java))
    }

    private fun performSearch() {
        binding.search.clearFocus()
        binding.search.text.clear()
        val input: InputMethodManager? =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input?.hideSoftInputFromWindow(binding.search.windowToken, 0)
        //...perform search
    }

    override fun onStateChange(position: Int, like: Boolean) {
        super.onStateChange(position, like)

        if (like) {
            var followList = peoplelist as ArrayList<PeopleListModel>
            followUser(followList[position].id!!)
        } else {
            var followList = peoplelist as ArrayList<PeopleListModel>
            unfollowUser(followList[position].id!!)
        }

    }

    private fun followUser(id: Int) {
        peopleView.userFollow(id, token)?.observe(this@ViewAll) { it ->
            when (it?.status) {
                Status.SUCCESS -> {
                    Toast.makeText(this@ViewAll, it.data?.message.toString(), Toast.LENGTH_SHORT)
                        .show()

                }
                Status.LOADING -> {}
                Status.ERROR -> {
                    Toast.makeText(this@ViewAll, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun unfollowUser(id: Int) {
        peopleView.userUnfollow(id, token)?.observe(this@ViewAll) { it ->
            when (it?.status) {
                Status.SUCCESS -> {
                    Toast.makeText(this@ViewAll, it.data?.message.toString(), Toast.LENGTH_SHORT)
                        .show()

                }
                Status.LOADING -> {}
                Status.ERROR -> {
                    Toast.makeText(this@ViewAll, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onclick(position: Int) {
        super.onclick(position)
        Comment_BottomSheet().show(supportFragmentManager, " ");
    }
}