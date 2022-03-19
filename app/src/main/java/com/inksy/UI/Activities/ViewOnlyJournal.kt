package com.inksy.UI.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.inksy.Model.Journals
import com.inksy.R
import com.inksy.UI.Constants
import com.inksy.UI.Dialogs.Comment_BottomSheet
import com.inksy.UI.Dialogs.ReportDialog
import com.inksy.UI.ViewModel.JournalView
import com.inksy.Utils.TinyDB
import com.inksy.databinding.ActivityViewOnlyJournalBinding


class ViewOnlyJournal() : AppCompatActivity() {
    var journalData: Journals? = null
    lateinit var binding: ActivityViewOnlyJournalBinding
    lateinit var like: ImageView
    lateinit var comment: ImageView
    lateinit var journalView: JournalView
    lateinit var like_counter: TextView
    lateinit var comment_counter: TextView
    lateinit var tinyDb: TinyDB
    var like_count: Int = 0
    var comment_count: Int = 0
    var followcounter: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewOnlyJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tinyDb = TinyDB(this)
        journalView = ViewModelProviders.of(this)[JournalView::class.java]
        journalView.init()
        var token = tinyDb.getString("token")
        Handler().postDelayed({
            binding.loader.visibility = View.GONE
        }, 3000)

        like = findViewById(R.id.like)
        comment = findViewById(R.id.comment)
        like_counter = findViewById(R.id.like_count)
        comment_counter = findViewById(R.id.comment_count)

        followcounter = 0
        binding.followCount.text = "0"

        journalData = null

        journalData = intent.getSerializableExtra("data") as? Journals

        if (journalData != null)
            getData(journalData!!.id!!, token!!)

        var string = intent.getStringExtra(Constants.journalType)

        binding.like.setOnClickListener {

            if (journalData != null) {
                if (journalData?.isJournalLike == 0) {
                    like_count++
                    like_counter.text = like_count.toString()
                    journalData?.isJournalLike = 1
                    likeJournal(journalData?.id, true, token!!)
                } else if (like_count == 1) {
                    journalData?.isJournalLike = 0
                    like_count--
                    like_counter.text = like_count.toString()
                    likeJournal(journalData?.id, true, token!!)
                }

                Log.d("-----", like_count.toString())
            } else {
                Toast.makeText(this, "Journal data is empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.comment.setOnClickListener {
            if (comment_count == 0) {
                comment_count++
                comment_counter.text = comment_count.toString()

                var comment = Comment_BottomSheet().let {
                    it.show(supportFragmentManager, "")

                }
            }
            Log.d("-----", comment_count.toString())
        }

        binding.follow.setOnClickListener()
        {
            if (journalData?.isJournalFollow == 0) {
                Glide.with(this).load(getDrawable(R.drawable.unfollowing)).into(binding.follow)
                journalData?.isJournalFollow = 1
                followcounter++
                binding.followCount.text = followcounter.toString()
                followJournal(journalData?.id, token!!)
            } else {
                journalData?.isJournalFollow = 0
                followcounter--
                binding.followCount.text = followcounter.toString()
                Glide.with(this).load(getDrawable(R.drawable.follow)).into(binding.follow)
                followJournal(journalData?.id, token!!)

            }
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

                        Toast.makeText(this, "Feature coming soon", Toast.LENGTH_SHORT).show()

                        return@OnMenuItemClickListener true
                    }
                    R.id.Report -> {

                        if (journalData != null) {
                            ReportDialog(
                                this,
                                this@ViewOnlyJournal,
                                this@ViewOnlyJournal,
                                this@ViewOnlyJournal,
                                journalData?.id!!.toString(),
                                "journal"
                            ).show()
                        } else {
                            Toast.makeText(this, "Journal data is empty", Toast.LENGTH_SHORT).show()
                        }

                        return@OnMenuItemClickListener true
                    }
                    R.id.block -> {
                        return@OnMenuItemClickListener true
                    }
                    R.id.View -> {

                        startActivity(
                            Intent(this, ShowJournal::class.java)
                                .putExtra("Edit", "Edit")
                                .putExtra("JSON", journalData?.htmlContent)
                        )
                        return@OnMenuItemClickListener true
                    }

                    else -> false
                }


            })
            popupMenu.inflate(R.menu.view_journal_popup)
            popupMenu.show()

            if (string.equals(Constants.person)) {

                popupMenu.menu.findItem(R.id.Report).isVisible = false
                popupMenu.menu.findItem(R.id.block).isVisible = false

            } else {
                popupMenu.menu.findItem(R.id.Delete).isVisible = false
                popupMenu.menu.findItem(R.id.edit).isVisible = false
                popupMenu.menu.findItem(R.id.block).isVisible = false

            }

        }


        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun likeJournal(id: Int?, like: Boolean, token: String) {

        journalView.journalLike(
            id.toString(),
            token
        )?.observe(this) {

            if (it?.data?.status == 1) {
                Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, it?.data?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun followJournal(id: Int?, token: String) {

        journalView.journalFollow(
            id.toString(),
            token
        )?.observe(this) {

            if (it?.data?.status == 1) {
                Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, it?.data?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getData(journalid: Int, token: String) {
        journalView.journalDetails(journalid, token)?.observe(this) {

            var journals = it?.data?.data
            journalData = journals
            populatedata(journals!!)
        }
    }

    fun populatedata(_journalData: Journals) {


        if (_journalData != null) {

            binding.journalTitle.text = _journalData.title
            binding.subtext.text = _journalData.category?.categoryName
            binding.subtext2.text = _journalData.description

            Glide.with(this).load(Constants.BASE_IMAGE + _journalData.coverImage)
                .into(binding.coverImage)

            like_count = _journalData?.likesCount!!
            comment_count = _journalData?.commentsCount!!

            like_counter.text = like_count.toString()
            comment_counter.text = comment_count.toString()
            binding.followCount.text = _journalData.followersCount.toString()
            followcounter = _journalData.followersCount!!

            if (journalData?.isJournalFollow == 0) {
                Glide.with(this).load(getDrawable(R.drawable.follow)).into(binding.follow)

            } else {

                Glide.with(this).load(getDrawable(R.drawable.unfollowing)).into(binding.follow)

            }


        }
    }
}