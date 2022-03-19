package com.inksy.UI.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.example.Pack
import com.inksy.Interfaces.OnChangeStateClickListener
import com.inksy.Interfaces.OnDialogClickListener
import com.inksy.Interfaces.iOnClickListerner
import com.inksy.Model.Journals
import com.inksy.Model.UserModel
import com.inksy.R
import com.inksy.Remote.Status
import com.inksy.UI.Adapter.ArtworkAdapter
import com.inksy.UI.Adapter.BookAdapter
import com.inksy.UI.Constants
import com.inksy.UI.Dialogs.Comment_BottomSheet
import com.inksy.UI.Dialogs.ReportDialog
import com.inksy.UI.Dialogs.TwoButtonDialog
import com.inksy.UI.ViewModel.JournalView
import com.inksy.UI.ViewModel.PeopleView
import com.inksy.Utils.TinyDB
import com.inksy.databinding.ActivityPeopleBinding
import com.inksy.databinding.TablayoutBinding
import java.io.Serializable


class People : AppCompatActivity(), iOnClickListerner, OnChangeStateClickListener {
    lateinit var peopleView: PeopleView
    lateinit var binding: ActivityPeopleBinding
    lateinit var data: UserModel
    var followersList: ArrayList<UserModel> = ArrayList()
    var list: ArrayList<Journals>? = ArrayList()
    var doodle_list: ArrayList<Pack> = ArrayList()
    var token: String = " "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPeopleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loader.visibility = View.VISIBLE
        data = intent.getSerializableExtra("Data") as UserModel

        peopleView = ViewModelProvider(this)[PeopleView::class.java]
        peopleView.init()
        var tinyDB = TinyDB(this)

        token = tinyDB.getString("token")!!
        var accountUser = tinyDB.getString("id")!!.toInt()


        if (data != null) {
            binding.title.text = data.fullName.toString()
            binding.bio.text = data.bio.toString()


            getdetails(data.id!!, token)

            if (data.avatar != null)
                Glide.with(this).load(Constants.BASE_IMAGE + data.avatar)
                    .placeholder(R.drawable.ic_empty_user)
                    .into(binding.circleImageView)
        }

        var v: TablayoutBinding = binding.include1
        v.tab1.text = "${data.fullName}'s Journal"


        var v1: TablayoutBinding = binding.include6
        v1.tab1.text = "${data.fullName}'s Art"

        binding.seeall1.setOnClickListener {
            startActivity(
                Intent(this, ViewAll::class.java).putExtra(
                    "activity",
                    Constants.sub_journalViewAll
                )
            )
        }
        binding.seeall2.setOnClickListener {
            startActivity(
                Intent(this, ViewAll::class.java).putExtra(
                    "activity",
                    Constants.doodleViewAll
                )
            )
        }


        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.more.setOnClickListener {
            val contextWrapper = ContextThemeWrapper(this, R.style.popupMenuStyle)
            val popupMenu = PopupMenu(
                contextWrapper, binding.more
            )
            popupMenu.setForceShowIcon(true)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->

                when (item.itemId) {
                    R.id.Delete -> {

                        return@OnMenuItemClickListener true
                    }
                    R.id.edit -> {

                        return@OnMenuItemClickListener true
                    }
                    R.id.Report -> {

                        if (data != null) {
                            ReportDialog(
                                this,
                                this,
                                this,
                                this,
                                data?.id!!.toString(),
                                "user"
                            ).show()
                        }

                        return@OnMenuItemClickListener true
                    }
                    R.id.block -> {
                        openDialog()
                        return@OnMenuItemClickListener true
                    }

                    else -> false
                }


            })
            popupMenu.inflate(R.menu.view_journal_popup)
            popupMenu.show()

            popupMenu.menu.findItem(R.id.edit).isVisible = false
            popupMenu.menu.findItem(R.id.Delete).isVisible = false
            popupMenu.menu.findItem(R.id.View).isVisible = false
        }
        binding.follow.setOnClickListener() {
            Glide.with(this).load(resources.getDrawable(R.drawable.unfollowing))
                .into(binding.follow)
        }

        binding.followedPeople.setOnClickListener() {
            var intent = Intent(this, ViewAll::class.java).putExtra(
                "activity",
                Constants.peopleViewAll
            ).putExtra("List", followersList as Serializable).putExtra("Data", true)
            startActivity(intent)
        }

        binding.chat.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java))
        }

//        binding.textView13.setOnClickListener() {
//            var intent = Intent(this, ViewAll::class.java).putExtra(
//                "activity",
//                Constants.peopleViewAll
//            )
//            startActivity(intent)
//        }

    }

    fun getdetails(id: Int, token: String) {
        peopleView.userDetail(id, token)?.observe(this) { it ->
            binding.loader.visibility = View.GONE
            when (it.status) {
                Status.ERROR -> {

                }
                Status.SUCCESS -> {
//                    Toast.makeText(this, it.data?.data?.fullName, Toast.LENGTH_SHORT).show()

                    if (it.data?.data?.isFollowed == 1) {
                        binding.follow.background = ContextCompat.getDrawable(this,R.drawable.unfollowing)
                    } else {
                        binding.follow.setBackgroundResource(R.drawable.follow)
                    }

                    binding.followedPeople.text =
                        "Followed by ${it.data?.data?.followerCount} People"

                    binding.points.text = it.data?.data?.points.toString()


                    list = it.data?.data?.journals
                    followersList = it.data?.data?.followers!!

                    doodle_list = it.data?.data?.doodles!!

                    if (list?.size == 0) {
                        binding.textView3.visibility = View.GONE
                        binding.textView4.visibility = View.GONE
                        binding.rvFriends.visibility = View.GONE
                        binding.seeall1.visibility = View.GONE
                    } else {
                        binding.textView3.visibility = View.VISIBLE
                        binding.textView4.visibility = View.VISIBLE
                        binding.rvFriends.visibility = View.VISIBLE
                        binding.seeall1.visibility = View.VISIBLE

                        binding.rvFriends.adapter =
                            BookAdapter(this, list!!, Constants.person, this, this)


                        binding.rvDoodle.adapter = ArtworkAdapter(this, doodle_list, "Pack")

                    }
                }
                Status.LOADING -> {

                }
            }

            //it.data?.data?.fullName
        }
    }

    override fun onStateChange(position: Int, like: Boolean) {
        super.onStateChange(position, like)
        if (like) {
            likeJournal(list?.get(position)?.id, like)
        } else {
            likeJournal(list?.get(position)?.id, like)
        }
    }

    override fun onclick(position: Int) {
        super.onclick(position)
        Comment_BottomSheet().show(supportFragmentManager, " ");
    }

    private fun likeJournal(id: Int?, like: Boolean) {
        val journalView: JournalView =
            ViewModelProviders.of(this)[JournalView::class.java]
        journalView.init()
        journalView.journalLike(
            id.toString(),
            token
        )?.observe(this) {

            if (it?.data?.status == 1) {
                Toast.makeText(this, it?.data.message, Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, it?.data?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openDialog() {
        val twoButtonDialog: TwoButtonDialog = TwoButtonDialog(
            this, "Block User",
            "Are you sure, You want to block this user?",
            getString(android.R.string.yes),
            getString(android.R.string.no),
            object : OnDialogClickListener {
                override fun onDialogClick(callBack: String?) {
                    if (callBack == "Yes") {
                        blockUser(data.id!!, token)
                    } else {

                    }
                }
            })
        twoButtonDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        twoButtonDialog.show()
    }

    fun blockUser(id: Int, token: String) {
        peopleView.userBlock(id, token)?.observe(this) {
            when (it.status) {
                Status.ERROR -> {

                }
                Status.SUCCESS -> {

                    Toast.makeText(this, it.data?.message, Toast.LENGTH_SHORT).show()
                    this.finish()
                }
                Status.LOADING -> {

                }
            }
        }
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.pop_up, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
}